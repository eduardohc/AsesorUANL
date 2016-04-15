package com.example.ehernandez.asesoruanl;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseUser;

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
    ParseUser user;

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

        user = ParseUser.getCurrentUser();
        setUserAttributes();
    }

    public void setUserAttributes(){

        boolean isVerified;

        et_name.setText("" + user.get("Name"));
        tv_usernanme.setText("" + user.getUsername());
        et_email.setText("" + user.getEmail());

        isVerified = user.getBoolean("emailVerified");
        if(isVerified){
            tv_emailVerified.setText("" + getResources().getString(R.string.verified));
        }else{
            tv_emailVerified.setText("" + getResources().getString(R.string.noVerified));
        }

        et_dependency.setText("" + user.get("Dependencia"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_changes, menu);
        //btn_cancel = menu.findItem(R.id.cancel);
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
