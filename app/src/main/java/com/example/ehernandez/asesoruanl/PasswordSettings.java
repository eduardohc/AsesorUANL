package com.example.ehernandez.asesoruanl;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Eduardo on 14/04/2016.
 */
public class PasswordSettings extends AppCompatActivity {

    private EditText et_newPassword;
    private EditText et_confNewPAssword;
    private TextView tv_message;
    private MenuItem btn_save;
    boolean isModified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_password);

        overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out_anim);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tv_toolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_toolbar.setText("" + getResources().getString(R.string.change_password));

        et_newPassword = (EditText) findViewById(R.id.et_newPassword);
        et_confNewPAssword = (EditText) findViewById(R.id.et_confirm_newPassword);
        tv_message = (TextView) findViewById(R.id.tv_resetPasswordMessage);

        passwordsChange();

    }

    public void passwordsChange(){
        et_newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_message.setVisibility(View.INVISIBLE);
                isModified = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_confNewPAssword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isModified) {
                    tv_message.setVisibility(View.INVISIBLE);
                    invalidateOptionsMenu();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public int confirmPassword(){
        String newpassword, confirm;

        newpassword = et_newPassword.getText().toString();
        confirm = et_confNewPAssword.getText().toString();

        if(newpassword.equals(confirm) && newpassword.length() > 5){
            return 1;
        }else if(!newpassword.equals(confirm)){
            return 2;
        }else if(newpassword.length() <= 5){
            return 3;
        }else{
            return 0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            //Intent intent = new Intent(this, Register.class);
            //startActivity(intent);
            finish();

            overridePendingTransition(
                    R.anim.left_to_right_in, R.anim.left_to_right_out);
        }

        if(id == R.id.action_save){
            int iscorrect;
            String password;

            //tv_message.setVisibility(View.INVISIBLE);
            password = et_newPassword.getText().toString();
            iscorrect = confirmPassword();

            switch (iscorrect){
                case 0:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                tv_message.setText("Hubo un error al cambiar contraseña. Intente de nuevo.");
                                tv_message.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    break;
                case 1:
                    ParseUser user = ParseUser.getCurrentUser();
                    user.setPassword(password);
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), "Contraseña guardada con éxito",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Error al guardar contraseña. Por favor intente de nuevo",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    finish();
                    overridePendingTransition(
                            R.anim.left_to_right_in, R.anim.left_to_right_out);
                    break;
                case 2:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                tv_message.setText("Contraseñas diferentes.");
                                tv_message.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    break;
                case 3:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                tv_message.setText("Contraseña debe ser mayor que 5 dígitos");
                                tv_message.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    break;
            }
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
    public void onBackPressed() {
        //super.onBackPressed();

    }
}
