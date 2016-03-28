package com.example.ehernandez.asesoruanl;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ehernandez on 23/03/2016.
 */
public class MyFullConsultanciesAdapter extends ArrayAdapter<Consultancy> {

    public MyFullConsultanciesAdapter(Context context, ArrayList<Consultancy> consultancy){
        super(context, 0, consultancy);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Consultancy consultancy = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.search_consultancy_layout, parent, false);
        }

        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_search_name);
        TextView tv_summary = (TextView) convertView.findViewById(R.id.tv_search_summary);
        TextView tv_hour = (TextView) convertView.findViewById(R.id.tv_search_hour);
        Button btn_sendEmail = (Button) convertView.findViewById(R.id.btn_sendEmail);

        tv_name.setText(consultancy.getName());
        tv_summary.setText(consultancy.getSummary());
        tv_hour.setText(consultancy.getHour());

        btn_sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SendEmail.class);
                intent.putExtra("Name", consultancy.getName());
                intent.putExtra("Summary", consultancy.getSummary());
                intent.putExtra("Hour", consultancy.getHour());
                intent.putExtra("Email", consultancy.getEmail());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
