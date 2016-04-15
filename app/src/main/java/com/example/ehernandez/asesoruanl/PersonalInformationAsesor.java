package com.example.ehernandez.asesoruanl;

import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by ehernandez on 26/02/2016.
 */
public class PersonalInformationAsesor extends AppCompatActivity {

    EditText et_name, et_email, et_department;
    TextView tv_message;
    String name, email, department;
    boolean emailDifferent;
    //boolean nameModified, emailModified, departmentModified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asesor_information);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tv_toolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tv_toolbar.setText("" + getResources().getString(R.string.personalInfo));

        et_name = (EditText) findViewById(R.id.et_info_name);
        et_email = (EditText) findViewById(R.id.et_info_email);
        et_department = (EditText) findViewById(R.id.et_info_department);
        tv_message = (TextView) findViewById(R.id.tv_info_message);

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //nameModified = true;
                invalidateOptionsMenu();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //emailModified = true;
                invalidateOptionsMenu();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        et_department.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //departmentModified = true;
                invalidateOptionsMenu();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_advance, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item= menu.findItem(R.id.action_advance);
        item.setEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        Boolean isVerified;
        //String message;
        int id = item.getItemId();
        isVerified = verifiedInformation();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_advance && isVerified) {
            ParseUser user = ParseUser.getCurrentUser();
            user.setEmail(email);
            user.put("Name", name);
            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        emailDifferent = true;
                    } else {
                        emailDifferent = false;
                        String message;
                        message = e.getMessage();
                        if (message.equals("the email address " + email + " has already been taken")) {
                            tv_message.setVisibility(View.VISIBLE);
                            tv_message.setText("Ya existe un asesor con este correo electronico.");
                        } else {
                            tv_message.setVisibility(View.VISIBLE);
                            tv_message.setText(message);
                        }
                    }
                }
            });

            ParseObject object = new ParseObject("Maestro");
            if(emailDifferent) {
                object.put("Name", name);
                object.put("Email", email);
                object.put("Department", department);
                object.put("Dependencia", "FIME");
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        tv_message.setVisibility(View.VISIBLE);
                        tv_message.setText("Tu información fue guardada exitosamente");
                        tv_message.setTextColor(getResources().getColor(R.color.appColor));
                    }
                });

                Thread timer = new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            CallAddAsesoryActivity();
                            finish();
                        }
                    }
                };
                timer.start();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public Boolean verifiedInformation(){

        name = et_name.getText().toString();
        email = et_email.getText().toString();
        department = et_department.getText().toString();

        if(name.length() <= 0 || email.length() <= 0 || department.length() <= 0){
            tv_message.setText("Llena todos los datos personales.");
            tv_message.setVisibility(View.VISIBLE);

            return false;
        }else{
            return true;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void CallAddAsesoryActivity(){
        Intent intent = new Intent(PersonalInformationAsesor.this, MyConsultancies.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }
}
