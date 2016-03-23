package com.example.ehernandez.asesoruanl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ehernandez on 17/03/2016.
 */
public class MySummaryListAdapter extends ArrayAdapter<Consultancy> {

    public MySummaryListAdapter(Context context, ArrayList<Consultancy> consultancy){
        super(context, 0, consultancy);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Consultancy consultancy = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.asesor_consultancy_layout, parent, false);
        }

        TextView tv_summary = (TextView) convertView.findViewById(R.id.tv_asesor_summary);
        TextView tv_hour = (TextView) convertView.findViewById(R.id.tv_asesor_hour);

        tv_summary.setText(consultancy.getSummary());
        tv_hour.setText(consultancy.getHour());

        return convertView;
    }
}
