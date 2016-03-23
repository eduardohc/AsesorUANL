package com.example.ehernandez.asesoruanl;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

/**
 * Created by ehernandez on 25/02/2016.
 */
public class Login extends AppCompatActivity implements View.OnClickListener{

    EditText et_username, et_password;
    TextView tv_message, tv_recover_password;
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
        tv_recover_password = (TextView) findViewById(R.id.tv_login_recoverPassword);

        et_username.addTextChangedListener(new TextWatcher() {
            //String username;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_message.setVisibility(View.INVISIBLE);
                /*username = s.toString();
                if(s.length() != username.length()) {
                    tv_message.setVisibility(View.INVISIBLE);
                }else{
                    tv_message.setVisibility(View.VISIBLE);
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            //String password;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_message.setVisibility(View.INVISIBLE);
                /*password = s.toString();
                if(s.length() != password.length()) {
                    tv_message.setVisibility(View.INVISIBLE);
                }else{
                    tv_message.setVisibility(View.VISIBLE);
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_recover_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecoverPassword();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_login_login){
            Login();
            //Toast.makeText(getApplication(), "Iniciando sesión...", Toast.LENGTH_LONG).show();
        }

        /*if(v.getId() == R.id.tv_login_recoverPassword){
            RecoverPassword();
            //Toast.makeText(getApplicationContext(), "Changing password", Toast.LENGTH_LONG).show();
        }*/
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
                        Intent intent = new Intent(Login.this, MyConsultancies.class);
                        startActivity(intent);
                        finish();
                    } else if(e.getMessage().equals("invalid login parameters")){
                        tv_message.setVisibility(View.VISIBLE);
                        tv_message.setText("Usuario o contraseña incorrectos. Prueba de nuevo.");//"Error al intentar inicio de sesión. Prueba de nuevo");
                    } else{
                        tv_message.setVisibility(View.VISIBLE);
                        tv_message.setText("Error al iniciar sesión. Prueba de nuevo");
                    }
                }
            });
        }
    }

    public void RecoverPassword(){

        // Create dialog to reset password
        //final TextView tv_message_recover;
        final Dialog dialog = new Dialog(Login.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.recover_password);
        dialog.show();

        // If recover button is click, sent an email to recover password
        final EditText et_recover_email = (EditText) dialog.findViewById(R.id.et_recover_email);
        Button btn_recover_recover = (Button) dialog.findViewById(R.id.btn_recover_recover);
        //tv_message_recover = (TextView) findViewById(R.id.tv_recover_message);
        btn_recover_recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email;
                email = et_recover_email.getText().toString();

                //Display an error message if the form isn't fill
                if (email.equals("")) {
                    et_recover_email.setHintTextColor(getResources().getColor(R.color.red));
                } else {
                    // Send email to the user who request password reset
                    ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                /*tv_message_recover.setVisibility(View.VISIBLE);
                                tv_message_recover.setText("Se ha enviado el correo.");
                                tv_message_recover.setTextColor(getResources().getColor(R.color.green));*/

                                Toast.makeText(getApplicationContext(), "Se ha enviado el correo.",
                                        Toast.LENGTH_SHORT).show();

                                Thread timer = new Thread() {
                                    @Override
                                    public void run() {
                                        try {
                                            sleep(1500);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } finally {
                                            dialog.dismiss();
                                        }
                                    }
                                };
                                timer.start();
                            } else {
                                String message;
                                message = e.getMessage();
                                Toast.makeText(getApplicationContext(), "" + message,
                                        Toast.LENGTH_SHORT).show();
                                /*tv_message_recover.setVisibility(View.VISIBLE);
                                tv_message_recover.setText("" + message);
                                tv_message_recover.setTextColor(getResources().getColor(R.color.red));*/
                            }
                        }
                    });

                }
            }
        });

        // Close dialog if button cancel is click
        Button cancel_recover = (Button) dialog.findViewById(R.id.btn_recover_cancel);
        cancel_recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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
