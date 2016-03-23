package com.example.ehernandez.asesoruanl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

        Consultancy consultancy = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.search_consultancy_layout, parent, false);
        }

        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_search_name);
        TextView tv_summary = (TextView) convertView.findViewById(R.id.tv_search_summary);
        TextView tv_hour = (TextView) convertView.findViewById(R.id.tv_search_hour);

        tv_name.setText(consultancy.getName());
        tv_summary.setText(consultancy.getSummary());
        tv_hour.setText(consultancy.getHour());

        return convertView;
    }
}
