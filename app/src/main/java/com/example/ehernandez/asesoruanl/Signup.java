package com.example.ehernandez.asesoruanl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by ehernandez on 25/02/2016.
 */
public class Signup extends AppCompatActivity implements View.OnClickListener{

    CheckBox chb_asesor, chb_alumno;
    EditText et_username, et_password, et_confpassword;
    TextView tv_message;
    ProgressDialog progressDialog;
    //Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tv_toolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_toolbar.setText("" + getResources().getString(R.string.signup));

        chb_asesor = (CheckBox) findViewById(R.id.chb_asesor);
        chb_alumno = (CheckBox) findViewById(R.id.chb_alumno);
        et_username = (EditText) findViewById(R.id.et_signup_username);
        et_password = (EditText) findViewById(R.id.et_signup_password);
        et_confpassword = (EditText) findViewById(R.id.et_signup_confpassword);
        tv_message = (TextView) findViewById(R.id.tv_signup_message);

        /*chb_asesor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //chb_asesor.setChecked(true);
                chb_alumno.setChecked(false);
                et_username.setText("Numero de empleado");
            }
        });

        chb_alumno.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //chb_alumno.setChecked(true);
                chb_asesor.setChecked(false);
                et_username.setText("Matricula de alumno");

            }
        });*/

    }

    public void onCheckboxClicked(View view){
        switch (view.getId()){
            case R.id.chb_asesor:
                chb_alumno.setChecked(false);
                et_username.setHint("Numero de empleado");
                break;
            case R.id.chb_alumno:
                chb_asesor.setChecked(false);
                et_username.setHint("Matricula");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_signup_register){
            isUserCorrect();
        }
    }

    public void isUserCorrect() {
        String username, password, confPassword;
        boolean isPasswordCorrect;
        boolean isAsesor = false;
        boolean isStudent = false;

        username = et_username.getText().toString();
        password = et_password.getText().toString();
        confPassword = et_confpassword.getText().toString();

        if (password.equals(confPassword)) {
            isPasswordCorrect = true;
        } else {
            isPasswordCorrect = false;
        }

        if (chb_asesor.isChecked() && username.startsWith("0") && username.length() == 6) {
            isAsesor = true;
            isStudent = false;
        } else if(chb_alumno.isChecked() && username.length() >= 7){
            isAsesor = false;
            isStudent = true;
        }

        if (username.equals("") || password.equals("") || confPassword.equals("")) {
            tv_message.setVisibility(View.VISIBLE);
            tv_message.setText("Llena todas las formas"); //+ R.string.fill_formas);
        } else if (isPasswordCorrect && isAsesor) {
            //tv_message.setVisibility(View.INVISIBLE);
            /*tv_message.setVisibility(View.VISIBLE);
            tv_message.setText("Asesor");*/
            ParseUser user = new ParseUser();
            user.setUsername(username);
            user.setPassword(password);
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    try {
                        if (e == null) {
                            // Create a progressDialog to display the sign up progress

                            progressDialog = ProgressDialog.show(Signup.this,
                                    "Please wait..", "Singing up user..", true);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        // Time progressDialog will be displayed
                                        Thread.sleep(10000);
                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                    }
                                    progressDialog.dismiss();
                                }
                            }).start();

                            OpenPersonalInformation("Asesor");

                        } else {
                            //Show a message with the ParseException to know the error
                            String message;
                            message = e.getMessage();
                            Toast.makeText(getApplicationContext(), " " + message,
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            });
        } else if (isPasswordCorrect && isStudent) {
            OpenPersonalInformation("Student");

            /*
            * Type code for student
            * */
        } else if (chb_asesor.isChecked() && !isAsesor){
            tv_message.setVisibility(View.VISIBLE);
            tv_message.setText("Numero de empleado no cumple los requisitos.");
        }else if(chb_alumno.isChecked() && !isStudent){
            tv_message.setVisibility(View.VISIBLE);
            tv_message.setText("Matricula no cumple los requisitos.");
        }
        /*username.length() < 5 || username.length() > 8) {
            if (chb_asesor.isChecked() && !isAsesor) {

            } else {


            // + R.string.password_different);
        }*/else if(!isPasswordCorrect){
            tv_message.setVisibility(View.VISIBLE);
            tv_message.setText("La contraseña es incorrecta");
        }
    }

    public void OpenPersonalInformation(String category){

        if(category.equals("Asesor")){
            Intent intent = new Intent(Signup.this, PersonalInformationAsesor.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(Signup.this, PersonalInformationStudent.class);
            startActivity(intent);
            finish();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Destroy the progressDialog when the activity exit
        if(progressDialog != null){
            if(progressDialog.isShowing()){
                progressDialog.cancel();
            }
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }
}
