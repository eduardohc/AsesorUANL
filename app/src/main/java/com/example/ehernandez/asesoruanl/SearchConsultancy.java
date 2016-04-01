package com.example.ehernandez.asesoruanl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 01/03/2016.
 */
public class SearchConsultancy extends AppCompatActivity{

    private ListView consultancyList;
    private TextView tv_message;
    ArrayList<Consultancy> fullConsultancyList;
    MyFullConsultanciesAdapter fullConsultanciesAdapter;

    //private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_asesory);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tv_toolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_toolbar.setText("" + getResources().getString(R.string.searchAsesory));

        tv_message = (TextView) findViewById(R.id.tv_searchconsultancy_message);

        fullConsultancyList = new ArrayList<>();
        AddConsultancy();
    }

    public void AddConsultancy(){
        consultancyList = (ListView) findViewById(R.id.lv_allConsultancies);

        Bundle bundle = getIntent().getExtras();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Materia");
        query.whereEqualTo("Materia", bundle.get("Materia"));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() <= 0) {
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText("No hay asesorias registradas.");
                } else {
                    for (int i = 0; i < objects.size(); i++) {
                        String name, summary, hour, objectId, email;

                        name = (String) objects.get(i).get("Nombre");
                        summary = (String) objects.get(i).get("Materia");
                        hour = (String) objects.get(i).get("Hora");
                        objectId = objects.get(i).getObjectId();
                        email = (String) objects.get(i).get("Email");

                        fullConsultancyList.add(
                                i, new Consultancy(name, summary, hour, objectId, email));
                    }

                    fullConsultanciesAdapter = new MyFullConsultanciesAdapter(
                            getApplicationContext(), fullConsultancyList);
                    consultancyList.setAdapter(fullConsultanciesAdapter);
                }
            }
        });
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }
}
