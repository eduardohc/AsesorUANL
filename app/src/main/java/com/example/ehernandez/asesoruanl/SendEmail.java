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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseUser;

/**
 * Created by Eduardo on 24/03/2016.
 */
public class SendEmail extends AppCompatActivity implements View.OnClickListener{

    private EditText body;
    String recipientText, subjectText;
    ParseUser user = ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_email);

        overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out_anim);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cancel);
        TextView tv_toolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        tv_toolbar.setText("" + getResources().getString(R.string.sendEmail));

        EditText recipient = (EditText) findViewById(R.id.et_emailasesor);
        EditText subject = (EditText) findViewById(R.id.et_emailsubject);
        body = (EditText) findViewById(R.id.et_emailbodyText);

        Bundle extras = getIntent().getExtras();
        String bodyText, name;

        recipientText = "" + extras.get("Email");
        subjectText = "Solicitud de Asesoría";

        name = "" + user.get("Name");

        if(name.equals("null") || name.equals("")){
            bodyText = "" + getResources().getText(R.string.email_gretting) + " " + extras.get("Name")
                    + "\n\n" + getResources().getText(R.string.email_beforeName) + " " +
                    extras.get("Summary") + " de " + extras.get("Hour") + ".";
        }else{
            bodyText = "" + getResources().getText(R.string.email_gretting) + " " + extras.get("Name")
                    + "\n\n" + "El alumno " + user.get("Name") + " esta solicitando una asesoría de la materia " +
                    extras.get("Summary") + " de " + extras.get("Hour") + ".";
        }


        recipient.setText("Asesor");
        subject.setText(subjectText);
        body.setText(bodyText);

    }

    protected void sendEmail(){

        Intent email = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:"));
        email.setType("text/plain");
        email.putExtra(Intent.EXTRA_EMAIL, new String[] {recipientText});
        email.putExtra(Intent.EXTRA_SUBJECT, subjectText);
        email.putExtra(Intent.EXTRA_TEXT, body.getText().toString());

        try {
            // the user can choose the email client
            startActivity(Intent.createChooser(email, "Choose an email client from..."));
            finish();

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SendEmail.this, "No email client installed.",
                    Toast.LENGTH_LONG).show();
        }
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

    /*@Override
    public void onBackPressed() {
        //super.onBackPressed();

    }*/
}
