package com.example.ehernandez.asesoruanl;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ehernandez on 28/03/2016.
 */
public class SearchByConsultancyAdapter extends ArrayAdapter<Consultancy> {

    public SearchByConsultancyAdapter(Context context, ArrayList<Consultancy> consultancy){
        super(context, 0, consultancy);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Consultancy consultancy = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.search_by_consultancy_list_layout, parent, false);
        }

        TextView tv_summary = (TextView) convertView.findViewById(R.id.tv_searchbyconsultancy_summary);
        TextView tv_asesors = (TextView) convertView.findViewById(R.id.tv_searchbyconsultancy_asesors);
        //Button btn_sendEmail = (Button) convertView.findViewById(R.id.btn_sendEmail);


        tv_summary.setText(consultancy.getSummary());
        tv_asesors.setText(consultancy.getConsultantsNumber());

        /*btn_sendEmail.setOnClickListener(new View.OnClickListener() {
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
        });*/

        return convertView;
    }
}
