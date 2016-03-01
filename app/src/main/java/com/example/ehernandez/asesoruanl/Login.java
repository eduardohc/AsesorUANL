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
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by ehernandez on 25/02/2016.
 */
public class Login extends AppCompatActivity implements View.OnClickListener{

    EditText et_username, et_password;
    TextView tv_message;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tv_toolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_toolbar.setText("" + getResources().getString(R.string.login));

        et_username = (EditText) findViewById(R.id.et_login_username);
        et_password = (EditText) findViewById(R.id.et_login_password);
        tv_message = (TextView) findViewById(R.id.tv_login_error);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_login_login){
            Login();
            //Toast.makeText(getApplication(), "Iniciando sesión...", Toast.LENGTH_LONG).show();
        }
    }

    public void Login(){
        String username, password;
        username = et_username.getText().toString();
        password = et_password.getText().toString();

        if(username.equals("") || password.equals("")){
            tv_message.setVisibility(View.VISIBLE);
            tv_message.setText("Llena todas las formas");
        } else{
            ParseUser.logInInBackground(username, password, new LogInCallback() {
                //@Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {
                        progressDialog = ProgressDialog.show(Login.this,
                                "Please wait..", "Logging in user...", true);
                        new Thread(new Runnable() {
                            //@Override
                            public void run() {
                                try {
                                    Thread.sleep(10000);
                                } catch (Exception exception) {

                                }
                                progressDialog.dismiss();

                            }
                        }).start();

                        //If everything was succesfull, open mainactivity
                        Intent intent = new Intent(Login.this, AddAsesory.class);
                        startActivity(intent);
                        finish();
                    } else {
                        tv_message.setVisibility(View.VISIBLE);
                        tv_message.setText("Error al intentar inicio de sesión. Prueba de nuevo");
                    }
                }
            });
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
            Intent intent = new Intent(Login.this, Register.class);
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
