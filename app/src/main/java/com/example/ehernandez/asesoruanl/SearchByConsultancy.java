
package com.example.ehernandez.asesoruanl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
        tv_toolbar.setText("" + getResources().getString(R.string.fullAsesoryList));

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

                TextView tv_summary = (TextView)
                        view.findViewById(R.id.tv_searchbyconsultancy_summary);
                TextView tv_asesors = (TextView)
                        view.findViewById(R.id.tv_searchbyconsultancy_asesors);

                for(int i = 0; i < consultancyList.getCount(); i++) {

                    if(position == consultancyList.getLastVisiblePosition()){
                        tv_summary.setSelected(false);
                        tv_asesors.setSelected(false);
                    } else if (position == i) {
                        tv_summary.setSelected(true);
                        tv_asesors.setSelected(true);
                        Log.d("Position", "" + position + " - " +
                                consultancyList.getLastVisiblePosition());
                    } else{
                        tv_summary.setSelected(false);
                        tv_asesors.setSelected(false);
                    }
                }

                Intent intent = new Intent(SearchByConsultancy.this, SearchConsultancy.class);
                intent.putExtra("Materia", arrayConsultancies.get(position).getSummary());
                startActivity(intent);
            }
        });
    }

    public void getConsultancies(){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Materia");
        query.orderByAscending("Clase");
        //query.addDescendingOrder("Clase");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                String asesorCount = "0", materia = "";
                if (objects.size() <= 0) {
                    tv_message.setVisibility(View.VISIBLE);
                    //tv_message.setText("No hay materias disponibles");
                } else {

                    HashMap<String, Integer> numberDuplicates = new HashMap<>();

                    for(int i = 0; i < objects.size(); i++){
                        materia = "" + objects.get(i).get("Clase");
                        numberDuplicates.put(materia, 0);
                    }

                    for(int i = 0; i < objects.size(); i++){
                        int count;
                        materia = "" + objects.get(i).get("Clase");
                        if(numberDuplicates.containsKey(materia)){
                            count = numberDuplicates.get(materia);
                            count += 1;
                            numberDuplicates.put(materia, count);
                        }
                    }

                    Map<String, Integer> treeMap = new TreeMap<>(numberDuplicates);

                    for(int i = 0; i < treeMap.size(); i++){
                        String summary = "" + treeMap.keySet().toArray()[i];
                        int totalSummaries = treeMap.get(summary);

                        if(totalSummaries == 1){
                            asesorCount = totalSummaries + " asesor";
                            arrayConsultancies.add(i, new Consultancy(summary, asesorCount));
                        } else if(totalSummaries > 1){
                            asesorCount = totalSummaries + " asesores";
                            arrayConsultancies.add(i, new Consultancy(summary, asesorCount));
                        }
                    }
                }

                searchByConsultancyAdapter = new SearchByConsultancyAdapter(getApplicationContext(),
                        arrayConsultancies);

                consultancyList.setAdapter(searchByConsultancyAdapter);

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
