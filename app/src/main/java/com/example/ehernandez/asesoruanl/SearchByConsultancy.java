
package com.example.ehernandez.asesoruanl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ehernandez on 28/03/2016.
 */
public class SearchByConsultancy extends AppCompatActivity{

    private ListView consultancyList;
    private ArrayList<Consultancy> arrayConsultancies;
    private TextView tv_message;
    SearchByConsultancyAdapter searchByConsultancyAdapter;
    private String[] consultancies;
    private String summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_by_consultancy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tv_toolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_toolbar.setText("" + getResources().getString(R.string.searchAsesory));

        tv_message = (TextView) findViewById(R.id.tv_searchbyconsultancy_message);
        consultancies = getResources().getStringArray(R.array.classes);
        arrayConsultancies = new ArrayList<>();
        consultancyList = (ListView) findViewById(R.id.lv_searchbyconsultancy_consultancies);

        if(consultancies.length > 0){
            getConsultancies();
        }else{
            tv_message.setText("Por el momento, no hay materias disponibles.");
        }

        consultancyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchByConsultancy.this, SearchConsultancy.class);
                intent.putExtra("Materia", arrayConsultancies.get(position).getSummary());
                startActivity(intent);
            }
        });
    }

    public void getConsultancies(){

        //arrayConsultancies.add(0, new Consultancy(consultancies[1], "5 asesores"));
        //final ArrayList<Consultancy> temporal = new ArrayList<>();
        //arrayConsultancies.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Materia");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                String asesorCount = "0", materia = "";
                if (objects.size() <= 0) {
                    tv_message.setVisibility(View.VISIBLE);
                    //tv_message.setText("No hay materias disponibles");
                } else {

                    List<String> temp = new ArrayList<String>();

                    for(int i = 0; i < objects.size(); i++){
                        materia = "" + objects.get(i).get("Materia");
                        temp.add(materia);
                    }

                    Set<String> hs = new HashSet<String>();
                    hs.addAll(temp);
                    temp.clear();
                    temp.addAll(hs);

                    

                    int count = 0;
                    for(int i=0; i < temp.size();i++){
                        //materia = "" + objects.get(i).get("Materia");
                        for(int j = 0; j < objects.size();j++){
                            materia = "" + objects.get(j).get("Materia");
                            if(temp.get(i).equals(materia)){
                                count++;
                            }
                        }

                        if(count == 1){
                            asesorCount = count + " asesor";
                            arrayConsultancies.add(i, new Consultancy(temp.get(i), asesorCount));
                            count = 0;
                        } else if(count > 1){
                            asesorCount = count + " asesores";
                            arrayConsultancies.add(i, new Consultancy(temp.get(i), asesorCount));
                            count = 0;
                        }
                    }

                    /*
                    *
                    ** THIS SECTION WORKS BUT IS NOT EFICIENCY **
                    *
                    *
                    boolean contain = false;
                    int count = 0;
                    int count2 = 0;
                    int arrayA = 0;


                    for(int i = 0; i < objects.size(); i++){
                        for(int j = i-1; j >= 0; j--){
                            if(objects.get(i).get("Materia").equals(objects.get(j).get("Materia"))){
                                count++;
                                Log.d("Materia repetida", "" + objects.get(j).get("Materia"));
                            }
                        }
                        materia = "" + objects.get(i).get("Materia");

                        if(count == 0){
                            for(int j = 0; j < objects.size(); j++){
                                if(objects.get(j).get("Materia").equals(objects.get(i).get("Materia"))){
                                    count2++;
                                }
                            }

                            if(count2 == 1){
                                asesorCount = count2 + " asesor";
                                arrayConsultancies.add(arrayA, new Consultancy(materia, asesorCount));
                                count2 = 0;
                                arrayA = arrayA + 1;
                            } else if(count2 > 1){
                                asesorCount = count2 + " asesores";

                                count2 = 0;
                                arrayA = arrayA + 1;
                            }
                            //contain = false;

                        }
                        count = 0;
                    }*/

                        //arrayConsultancies.add(i, new Consultancy(consultancies[i], asesorCount));*/
                    }

                    searchByConsultancyAdapter = new SearchByConsultancyAdapter(getApplicationContext(),
                            arrayConsultancies);
                    consultancyList.setAdapter(searchByConsultancyAdapter);

                //}
            }
        });
    }

    public void addConsultancy(int position, String summary, String asesorCount){

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
