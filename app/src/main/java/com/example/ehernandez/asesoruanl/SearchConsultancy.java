package com.example.ehernandez.asesoruanl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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
    FullConsultanciesAdapter fullConsultanciesAdapter;
    Bundle bundle;
    //private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_asesory);

        overridePendingTransition(R.anim.right_to_left_in, R.anim.right_to_left_out_anim);

        bundle = getIntent().getExtras();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tv_toolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(bundle.getString("Materia").length() > 20){
            tv_toolbar.setText("" + getResources().getString(R.string.searchAsesory));
        }else {
            tv_toolbar.setText("" + bundle.getString("Materia"));
        }

        tv_message = (TextView) findViewById(R.id.tv_searchconsultancy_message);

        fullConsultancyList = new ArrayList<>();

        AddConsultancy();
    }

    public void AddConsultancy(){
        consultancyList = (ListView) findViewById(R.id.lv_allConsultancies);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Materia");
        query.whereEqualTo("Clase", bundle.get("Materia"));
        query.addAscendingOrder("Hora");
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
                        summary = (String) objects.get(i).get("Clase");
                        hour = (String) objects.get(i).get("Hora");
                        objectId = objects.get(i).getObjectId();
                        email = (String) objects.get(i).get("Email");

                        fullConsultancyList.add(
                                i, new Consultancy(name, summary, hour, objectId, email));
                    }

                    fullConsultanciesAdapter = new FullConsultanciesAdapter(
                            getApplicationContext(), fullConsultancyList);
                    fullConsultanciesAdapter.notifyDataSetChanged();
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
