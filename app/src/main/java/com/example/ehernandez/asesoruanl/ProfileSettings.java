package com.example.ehernandez.asesoruanl;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
 * Created by Eduardo on 14/04/2016.
 */
public class ProfileSettings extends AppCompatActivity {

    private EditText et_name;
    private TextView tv_usernanme;
    private EditText et_email;
    private TextView tv_emailVerified;
    private EditText et_department;
    private EditText et_dependency;
    private MenuItem btn_save;
    ParseUser user;
    boolean isModified;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_profile);

        overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out_anim);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tv_toolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_toolbar.setText("" + getResources().getString(R.string.profile));

        et_name = (EditText) findViewById(R.id.et_profileSettings_name);
        tv_usernanme = (TextView) findViewById(R.id.tv_profilesettings_username);
        et_email = (EditText) findViewById(R.id.et_profileSettings_email);
        tv_emailVerified = (TextView) findViewById(R.id.tv_profileSettings_emailVerified);
        et_department = (EditText) findViewById(R.id.et_profileSettings_department);
        et_dependency = (EditText) findViewById(R.id.et_profileSettings_dependency);
        et_name.setSelection(et_name.getText().length());
        et_email.setSelection(et_email.getText().length());
        et_department.setSelection(et_department.getText().length());
        et_dependency.setSelection(et_dependency.getText().length());

        user = ParseUser.getCurrentUser();
        setUserAttributes();
        editTextClicks();
    }

    public void setUserAttributes(){

        boolean isVerified;

        et_name.setText("" + user.get("Name"));
        tv_usernanme.setText("" + user.getUsername());
        et_email.setText("" + user.getEmail());

        //verification = "" + user.get("emailVerified");
        isVerified = user.getBoolean("emailVerified");
        if(isVerified){//verification.equals("true")){
            tv_emailVerified.setText("" + getResources().getString(R.string.verified));
        }else{
            tv_emailVerified.setText("" + getResources().getString(R.string.noVerified));
            Log.d("Verified", "" + isVerified);
        }

        String department;
        department = "" + user.get("Department");
        if(department.equals("null") || department.equals("")){
            et_department.setHint("Escriba aquí el departamento o coordinación");
        }else{
            et_department.setText("" + user.get("Department"));
        }

        et_dependency.setText("" + user.get("Dependencia"));

    }

    public void editTextClicks(){
       et_name.addTextChangedListener(new TextWatcher() {
           String name = et_name.getText().toString();
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

               //Toast.makeText(getApplicationContext(),"Click", Toast.LENGTH_SHORT).show();
               if(s.equals(name)){//et_name.getText().toString().equals(name)){
                   isModified = false;
                   invalidateOptionsMenu();
               }else{
                   isModified = true;
                   invalidateOptionsMenu();
               }

           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });

        et_email.addTextChangedListener(new TextWatcher() {
            String email = et_email.getText().toString();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.equals(email)){//et_name.getText().toString().equals(name)){
                    isModified = false;
                    invalidateOptionsMenu();
                }else{
                    isModified = true;
                    invalidateOptionsMenu();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_department.addTextChangedListener(new TextWatcher() {
            String department = et_department.getText().toString();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.equals(department)) {//et_name.getText().toString().equals(name)){
                    isModified = false;
                    invalidateOptionsMenu();
                } else {
                    isModified = true;
                    invalidateOptionsMenu();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /*et_dependency.addTextChangedListener(new TextWatcher() {
            String dependency = et_dependency.getText().toString();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.equals(dependency)){//et_name.getText().toString().equals(name)){
                    isModified = false;
                    invalidateOptionsMenu();
                }else{
                    isModified = true;
                    invalidateOptionsMenu();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

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
        String name, email, deparment, dependency, username;

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            //Intent intent = new Intent(this, Register.class);
            //startActivity(intent);
            finish();

            overridePendingTransition(
                    R.anim.left_to_right_in, R.anim.left_to_right_out);
        }

        if(id == R.id.action_save){

            name = et_name.getText().toString();
            email = et_email.getText().toString();
            deparment = et_department.getText().toString();
            //dependency = et_dependency.getText().toString();

            username = user.getUsername();
            Log.d("USername", username);
            user.put("Name", name);
            user.put("email", email);
            user.put("Department", deparment);
            //user.put("Dependencia", dependency);
            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(getApplicationContext(), "Información guardada correctamente.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "" + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                }
            });

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Materia");
            query.whereEqualTo("Usuario", username);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        for (int i = 0; i < objects.size(); i++) {
                            String objectId = objects.get(i).getObjectId();
                            changeNameInParse(objectId);
                        }
                    }
                }
            });

            finish();
            overridePendingTransition(
                    R.anim.left_to_right_in, R.anim.left_to_right_out);
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeNameInParse(String objectId){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Materia");
        query.whereEqualTo("objectId", objectId);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                String newName = et_name.getText().toString();
                if(e == null && !object.get("Nombre").equals(newName)){
                    object.put("Nombre", newName);
                    object.saveInBackground();
                }
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
    public void onBackPressed() {
        //super.onBackPressed();

    }
}
