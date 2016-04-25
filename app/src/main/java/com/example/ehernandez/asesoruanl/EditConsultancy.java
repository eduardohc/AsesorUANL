package com.example.ehernandez.asesoruanl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by ehernandez on 18/03/2016.
 */
public class EditConsultancy extends AppCompatActivity {

    NumberPicker editHours;
    AutoCompleteTextView editSummary;
    MenuItem btn_save;
    Bundle extras;
    String[] values = {"07:00 - 07:50", "07:50 - 08:40", "08:40 - 09:30", "09:30 - 10:20",
            "10:20 - 11:10", "11:10 - 12:00", "12:00 - 12:50", "12:50 - 13:40", "13:40 - 14:30",
            "14:30 - 15:20", "15:20 - 16:10", "16:10 - 17:00", "17:00 - 17:40", "17:40 - 18:20",
            "18:20 - 19:00", "19:00 - 19:40", "19:40 - 20:20", "20:20 - 21:00"};
    String summary, objectId;
    boolean isModified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_consultancy);

        overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out_anim);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tv_toolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_toolbar.setText("Editar asesoría");

        String [] allClasses = getResources().getStringArray(R.array.classes);
        ArrayAdapter<String> classes = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, allClasses);

        /*TextView tv_resume = (TextView) findViewById(R.id.tv_editasesor_resume);
        Typeface typeface_title = Typeface.createFromAsset(
                getAssets(), "fonts/Montserrat-Light.otf");
        tv_resume.setTypeface(typeface_title);*/

        editSummary = (AutoCompleteTextView) findViewById(R.id.et_editasesor_class);
        editSummary.setAdapter(classes);
        editSummary.setThreshold(0);
        editSummary.setDropDownBackgroundResource(R.color.appColor);

        editHours = (NumberPicker) findViewById(R.id.np_editasesor_hours);
        editHours.setMinValue(0);
        editHours.setMaxValue(17);
        editHours.setDisplayedValues(values);
        editHours.setWrapSelectorWheel(false);
        editHours.setValue(0);

        extras = getIntent().getExtras();

        if(extras != null){
            putExtrasOnFields();
        }

    }

    public void putExtrasOnFields(){

        int hour;

        summary = extras.getString("Summary");
        hour = getHourValue(extras.getString("Hour"));

        editSummary.setText(summary);
        editSummary.setSelection(editSummary.getText().length());

        editSummary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editSummary.getText().toString().equals(s)) {
                    isModified = false;
                } else {
                    isModified = true;
                    invalidateOptionsMenu();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        editHours.setValue(hour);
        editHours.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(newVal != oldVal){
                    isModified = true;
                    invalidateOptionsMenu();
                }
            }
        });
    }

    public int getHourValue(String hour){

        int value = 0;

        for(int i = 0 ;i < values.length; i++){
            if(hour.equals(values[i])){
                value = i;
            }
        }

        return value;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.save_changes, menu);
        btn_save = menu.findItem(R.id.action_save);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if(isModified){
            btn_save.setVisible(true);
        } else{
            btn_save.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home){
            //Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED);
            finish();

            overridePendingTransition(
                    R.anim.left_to_right_in, R.anim.left_to_right_out);
        }

        if(id == R.id.action_save){

            objectId = extras.getString("ObjectId");

            final ParseQuery<ParseObject> object = ParseQuery.getQuery("Materia");
            object.whereEqualTo("objectId", objectId);
            object.getInBackground(objectId, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        String newSummary, newHour;

                        newSummary = editSummary.getText().toString();
                        newHour = values[editHours.getValue()];

                        object.put("Clase", newSummary);
                        object.put("Hora", newHour);
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e != null) {
                                    Toast.makeText(getApplicationContext(),
                                            "Error al intentar agregar materia. Intente de nuevo.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });

            setResult(Activity.RESULT_OK);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    @Override
    public void onBackPressed() {}

}
