package com.example.ehernandez.asesoruanl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by ehernandez on 24/02/2016.
 */
public class OverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_activity);

        //Sleep the activity for some seconds
        Thread timer = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    CallMainActivity();
                }
            }
        }; timer.start();
    }

    //Method that calls at the main activity
    private void CallMainActivity() {
        Intent openMainActivity = new Intent(OverviewActivity.this, MainActivity.class);
        startActivity(openMainActivity);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(1);
    }
}
