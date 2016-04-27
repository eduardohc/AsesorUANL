package com.example.ehernandez.asesoruanl;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by ehernandez on 14/03/2016.
 */
public class AddAsesorConsultancy extends AppCompatActivity implements View.OnClickListener{

    NumberPicker hours;
    AutoCompleteTextView et_class;
    MenuItem btn_save;

    String[] values = {"07:00 - 07:50", "07:50 - 08:40", "08:40 - 09:30", "09:30 - 10:20",
            "10:20 - 11:10", "11:10 - 12:00", "12:00 - 12:50", "12:50 - 13:40", "13:40 - 14:30",
            "14:30 - 15:20", "15:20 - 16:10", "16:10 - 17:00", "17:00 - 17:40", "17:40 - 18:20",
            "18:20 - 19:00", "19:00 - 19:40", "19:40 - 20:20", "20:20 - 21:00"};
    ArrayAdapter<String> classes;
    boolean isModified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_asesor_consultancy);

        overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out_anim);

        String [] allClasses = getResources().getStringArray(R.array.classes);
        classes = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allClasses);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cancel);
        TextView tv_toolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        tv_toolbar.setText("" + getResources().getString(R.string.addAsesory));

        et_class = (AutoCompleteTextView) findViewById(R.id.et_addasesor_class);
        et_class.setAdapter(classes);
        et_class.setThreshold(0);
        et_class.setDropDownBackgroundResource(R.color.appColor);

        hours = (NumberPicker) findViewById(R.id.np_addasesor_hours);
        hours.setMinValue(0);
        hours.setMaxValue(17);
        hours.setDisplayedValues(values);
        hours.setWrapSelectorWheel(false);
        hours.setValue(0);

        et_class.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){
                    isModified = false;
                    invalidateOptionsMenu();
                }else {
                    isModified = true;
                    invalidateOptionsMenu();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.toolbar_action_cancel){
            setResult(Activity.RESULT_CANCELED);
            finish();

            overridePendingTransition(
                    R.anim.left_to_right_in, R.anim.left_to_right_out);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_changes, menu);
        btn_save = menu.findItem(R.id.action_save);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //super.onPrepareOptionsMenu(menu);
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
        boolean isVerified;
        isVerified = verifiedClass();
        //noinspection SimplifiableIfStatement

        if(item.getItemId() == R.id.action_save && isVerified){
            ParseUser user = ParseUser.getCurrentUser();
            ParseObject addClass = new ParseObject("Materia");
            addClass.put("Usuario", user.getUsername());
            addClass.put("Nombre", user.get("Name"));
            addClass.put("Email", user.getEmail());
            addClass.put("Clase", et_class.getText().toString());
            addClass.put("Hora", values[hours.getValue()]);
            addClass.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        setResult(Activity.RESULT_OK);
                    } else {
                        setResult(Activity.RESULT_CANCELED);
                    }
                }
            });

            finish();

            overridePendingTransition(
                    R.anim.left_to_right_in, R.anim.left_to_right_out);
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean verifiedClass(){

        if(et_class.length() > 0){
            return true;
        }else{
            return false;
        }
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
