package com.example.yzha502.reminder_android.reminder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.yzha502.reminder_android.R;
import com.example.yzha502.reminder_android.models.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by yzha502 on 25/04/15.
 */
public class EventAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Event> events;
    public EventAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }


    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_cell, null); // List layout here
        }

        TextView eventTitle = (TextView)convertView.findViewById(R.id.TitleTextViewInCell);
        TextView eventDescription = (TextView)convertView.findViewById(R.id.descriptionInCell);
        TextView eventTime = (TextView)convertView.findViewById(R.id.TimeTextViewinCell);

        eventTitle.setText(events.get(position).getTitle());
        eventDescription.setText(events.get(position).getDescription());

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(events.get(position).getTime());

        eventTime.setText(reportDate);

        boolean eventCompleted = events.get(position).isCompleted();
        if (eventCompleted==true)
            eventTitle.setTextColor(Color.parseColor("#00FF00")); // Green
        else
            eventTitle.setTextColor(Color.parseColor("#FF0000")); // Red
        return convertView;
    }
}
