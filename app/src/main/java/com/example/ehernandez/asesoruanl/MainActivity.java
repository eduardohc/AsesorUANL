package com.example.ehernandez.asesoruanl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tv_toolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tv_toolbar.setText("" + getResources().getString(R.string.app_name));
    }

    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){
            case R.id.btn_search_asesory:
                intent = new Intent(this, SearchByConsultancy.class);
                startActivity(intent);
                break;
            case R.id.btn_asesor_register:
                AuthenticateUser();
                //intent = new Intent(this, Register.class);
                //startActivity(intent);
                break;
        }    }

    public void AuthenticateUser(){
        //Determinate wheter the current user is an anonymous user
        if(ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())){
            //If user is anonymus, send to register himself
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
            //finish();
        } else{
            //If current user is not and anonymous user, get current user data from Parse.com
            ParseUser currentUser = ParseUser.getCurrentUser();
            if(currentUser != null){
                //Send user to mainActivity
                if(currentUser.getEmail() != null){
                    Intent intent = new Intent(this, MyConsultancies.class);
                    startActivity(intent);
                } else if(currentUser.get("Ocupacion").equals("Asesor")){
                    Intent intent = new Intent(this, PersonalInformationAsesor.class);
                    startActivity(intent);
                } else{
                    Intent intent = new Intent(this, PersonalInformationStudent.class);
                    startActivity(intent);
                }
                //finish();
            } else{
                // Send to RegisterUser
                Intent intent = new Intent(this, Register.class);
                startActivity(intent);
                //finish();
            }
        }
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
