package com.example.ehernandez.asesoruanl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ehernandez on 27/04/2016.
 */
public class SupportSettings extends AppCompatActivity implements View.OnClickListener{

    private EditText bodyText;
    String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_support);

        overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out_anim);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cancel);
        TextView tv_toolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        tv_toolbar.setText("" + getResources().getString(R.string.settings_support));

        Spinner feedbacks = (Spinner) findViewById(R.id.spinner_support_feedbacks);
        feedbacks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subject = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bodyText = (EditText) findViewById(R.id.et_support_bodyText);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.toolbar_action_cancel){
            setResult(Activity.RESULT_CANCELED);
            finish();

            overridePendingTransition(
                    R.anim.left_to_right_in, R.anim.left_to_right_out);
        }
    }

    public void sendEmail(){

        String body, recipient;

        recipient = "soporteasesoruanl@gmail.com";
        body = bodyText.getText().toString();

        Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
        email.setType("text/plain");
        email.putExtra(Intent.EXTRA_EMAIL, new String[] {recipient});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, body);

        try {
            // the user can choose the email client
            startActivity(Intent.createChooser(email, "Choose an email client from..."));
            finish();

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SupportSettings.this, "No email client installed.",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_email, menu);
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

        if(id == R.id.action_send){
            sendEmail();
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
}
