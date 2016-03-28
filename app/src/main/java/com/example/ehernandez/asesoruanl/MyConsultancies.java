package com.example.ehernandez.asesoruanl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ehernandez on 26/02/2016.
 */
public class MyConsultancies extends AppCompatActivity {

    private ListView summaryList;
    ArrayList<Consultancy> consultancyList;
    MySummaryListAdapter mySummaryListAdapter;
    LinearLayout layout_btns;
    ViewGroup.LayoutParams params;
    //MenuItem btn_cancel;/
    private TextView tv_message;
    private int pos;
    private boolean hasConsultancy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_consultancy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView tv_toolbar = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_toolbar.setText("" + getResources().getString(R.string.myConsultancies));

        tv_message = (TextView) findViewById(R.id.tv_addconsultancy_message);
        addData();
    }

    public void addData(){
        summaryList = (ListView) findViewById(R.id.lv_summary);
        //tv_test = (TextView) findViewById(R.id.tv_test);
        ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Materia");
        query.whereEqualTo("Usuario", user.getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                consultancyList = new ArrayList<>();
                if (objects.size() <= 0) {
                    tv_message.setVisibility(View.VISIBLE);
                    //tv_message.setText("No tienes ninguna asesoría registrada. Presiona + para agregar asesoría.");
                    hasConsultancy = false;
                } else {
                    tv_message.setVisibility(View.INVISIBLE);
                    hasConsultancy = true;
                    for (int i = 0; i < objects.size(); i++) {
                        String name, summary, hour, objectId;
                        name = (String) objects.get(i).get("Nombre");
                        summary = (String) objects.get(i).get("Materia");
                        hour = (String) objects.get(i).get("Hora");
                        objectId = objects.get(i).getObjectId();

                        consultancyList.add(i, new Consultancy(name, summary, hour, objectId));
                    }

                    mySummaryListAdapter = new MySummaryListAdapter(
                            getApplicationContext(), consultancyList);
                    summaryList.setAdapter(mySummaryListAdapter);

                }
            }
        });
    }

    private void AddAsesorConsultancy() {
        Intent intent = new Intent(MyConsultancies.this, AddAsesorConsultancy.class);
        startActivity(intent);
    }

    private void AddStudentConsultancy() {
        Intent intent = new Intent(MyConsultancies.this, AddStudentConsultancy.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == android.R.id.home){
            finish();
        } else if (id == R.id.addConsultancy) {
            ParseUser user = ParseUser.getCurrentUser();//.getParseUser("Name");
            String occupancy = "" + user.get("Ocupacion");
            if(occupancy == "Asesor"){
                AddAsesorConsultancy();
                //tv_message.setVisibility(View.INVISIBLE);
            } else{
                AddStudentConsultancy();
                //tv_message.setVisibility(View.INVISIBLE);
            }
        }else if(id == R.id.logout){
            ParseUser.logOut();
            Intent intent = new Intent(MyConsultancies.this, Register.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.editConsultancy && hasConsultancy){

            Button btn_cancel = (Button) findViewById(R.id.btn_cancel);
            Button btn_delete = (Button) findViewById(R.id.btn_delete);
            Button btn_edit = (Button) findViewById(R.id.btn_edit);

            layout_btns = (LinearLayout) findViewById(R.id.layout_btns_edit);
            params = layout_btns.getLayoutParams();
            params.height = 82;
            layout_btns.setLayoutParams(params);

            summaryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    pos = position;

                }
            });

            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    params.height = (0);
                    layout_btns.setLayoutParams(params);
                }
            });

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteConsultancy(pos);
                }
            });

            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editConsultancy(pos);
                }
            });

        }else if(!hasConsultancy){
            tv_message.setText("No tienes asesorias que editar.");
        }

        return super.onOptionsItemSelected(item);
    }

    public void editConsultancy(int position){

        Intent intent = new Intent(MyConsultancies.this, EditConsultancy.class);
        intent.putExtra("Summary", consultancyList.get(position).getSummary());
        intent.putExtra("Hour", consultancyList.get(position).getHour());
        intent.putExtra("ObjectId", consultancyList.get(position).getObjectId());
        startActivity(intent);

    }

    public void deleteConsultancy(int position){

        if(!consultancyList.isEmpty()){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Materia");
            query.whereEqualTo("objectId", consultancyList.get(position).getObjectId());
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    try{
                        object.delete();
                        object.saveInBackground();
                    }catch (ParseException ex){
                        ex.getMessage();
                    }
                }
            });

            consultancyList.remove(position);
            mySummaryListAdapter.notifyDataSetChanged();
        }else {
            tv_message.setVisibility(View.VISIBLE);
            tv_message.setText("No tienes ninguna asesoría registrada. Presiona + para agregar asesoría.");
            hasConsultancy = false;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

    }
}