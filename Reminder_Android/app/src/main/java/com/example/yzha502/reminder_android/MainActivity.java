package com.example.yzha502.reminder_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yzha502.reminder_android.models.DatabaseHelper;
import com.example.yzha502.reminder_android.models.Event;
import com.example.yzha502.reminder_android.reminder.EventAdapter;

import java.util.ArrayList;


public class MainActivity extends Activity {

    public static final int ADD_EVENT_REQUEST = 1;
    private static final int CHANGE_DETAIL_REQUEST=2;
    private ListView eventList;
    private ArrayList<Event> events;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventList = (ListView)findViewById(R.id.listView);
        events = new ArrayList<Event>();

        dbHelper = new DatabaseHelper(getApplicationContext());
        events = new ArrayList<Event>(dbHelper.getAllEvents().values());

        if(events.size()>=2)
            sortByDueDay(events);



        EventAdapter adapter = new EventAdapter(this, events);
        eventList.setAdapter(adapter);


        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent i = new Intent(getApplicationContext(), EventDetail.class);
                Event event = events.get(position);
                //events.remove(position);
                i.putExtra("event",event);
                startActivity(i);
                //startActivityForResult(i, CHANGE_DETAIL_REQUEST);
            }
        });


        eventList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                dbHelper.removeEvent(events.get(position));
                events = new ArrayList<Event>(dbHelper.getAllEvents().values());
                EventAdapter adapter = new EventAdapter(getApplicationContext(), events);
                eventList.setAdapter(adapter);
                return true;
            }
        });

        updateEvent();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_add:
// Move to AddMonsterActivity and await result
                Intent i = new Intent(this, AddEvent.class);
                i.putExtra("eventsSize", events.size());
                startActivity(i);
                //startActivityForResult(i, ADD_EVENT_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == ADD_EVENT_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                Event e = data.getParcelableExtra("result");
//                events.add(e);
//                if(events.size()>=2)
//                    sortByDueDay(events);
//
//                eventList.setAdapter(new EventAdapter(this, events));
//                updateEvent();
//            }
//        }
//        if (requestCode == CHANGE_DETAIL_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                Event e = data.getParcelableExtra("eventDetail");
//                events.add(e);
//                if(events.size()>=2)
//                    sortByDueDay(events);
//                eventList.setAdapter(new EventAdapter(this, events));
//                updateEvent();
//            }
//        }
//
//    }

    private void updateEvent() {

        int totalMonsters = events.size();
        TextView totalEvents = (TextView)findViewById(R.id.totalEvents);
        totalEvents.setText("Total Events: " + totalMonsters);
    }

    private void sortByDueDay(ArrayList<Event> li)
    {
        int size=li.size();
        System.out.print(size);
        for (int i=1; i<size ; i++)
        {
            for (int j=i;j>0;j--)
            {

                if (li.get(j).getTime().after(li.get(j-1).getTime()))
                {
                    Event ess = li.get(i);
                    Event esd = li.get(j);
                    li.remove(i);
                    li.add(i,esd);
                    li.remove(j);
                    li.add(j,ess);
                }
            }
        }

    }

}
