package com.example.ehernandez.asesoruanl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

/**
 * Created by ehernandez on 29/03/2016.
 */
public class Settings extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out_anim);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tv_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        tv_title.setText("" + getResources().getString(R.string.action_settings));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_settings_profile:
                Toast.makeText(getApplicationContext(), "Click on profile",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_settings_password:
                Toast.makeText(getApplicationContext(), "Click on password",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_settings_support:
                Toast.makeText(getApplicationContext(), "Click on Support",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_settings_privacyPolicy:
                Toast.makeText(getApplicationContext(), "Click on Privacy Policy",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_settings_termsOfService:
                Toast.makeText(getApplicationContext(), "Click on Terms of Service",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_settings_license:
                Toast.makeText(getApplicationContext(), "Click on License",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_settings_logout:
                ParseUser.logOut();
                Intent intent = new Intent(Settings.this, Register.class);
                startActivity(intent);
                finish();
                break;
        }
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
    public void onBackPressed() {
        //super.onBackPressed();

    }

}
