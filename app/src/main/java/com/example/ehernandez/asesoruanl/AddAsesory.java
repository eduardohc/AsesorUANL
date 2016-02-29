package com.example.ehernandez.asesoruanl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseUser;

/**
 * Created by ehernandez on 26/02/2016.
 */
public class AddAsesory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_asesory);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tv_toolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_toolbar.setText("" + getResources().getString(R.string.addAsesory));
    }

    @Override
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
        if (id == android.R.id.home){
            finish();
        }

        if (id == R.id.addAsesory) {
            return true;
        }

        if(id == R.id.logout){
            ParseUser.logOut();
            //Intent intent = new Intent(AddAsesory.this, Register.class);
            //startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }
}
