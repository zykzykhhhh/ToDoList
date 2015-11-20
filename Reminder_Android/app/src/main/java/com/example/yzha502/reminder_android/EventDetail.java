package com.example.yzha502.reminder_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.yzha502.reminder_android.models.DatabaseHelper;
import com.example.yzha502.reminder_android.models.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class EventDetail extends Activity {

    private TextView title;
    private TextView description;
    private TextView time;
    private CheckBox completed;
    private Button saveEvent;
    private Button updateEvent;
    //private Button deleteEvent;
    private Event event;
    private DatabaseHelper dbHelper;


    private static final int CHANGE_DETAIL_REQUEST=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        title = (TextView)findViewById(R.id.titleDetail);
        description=(TextView)findViewById(R.id.descriptionDetail);
        time=(TextView)findViewById(R.id.timeDetail);
        completed=(CheckBox)findViewById(R.id.completedInDetail);
        saveEvent=(Button)findViewById(R.id.saveDetail);
        updateEvent=(Button)findViewById(R.id.updateInDetail);
        //deleteEvent=(Button)findViewById(R.id.deleteInDetail);

        event=new Event();
        dbHelper = new DatabaseHelper(getApplicationContext());


        Intent i = getIntent();
        event = (Event)i.getParcelableExtra("event");
        title.setText(event.getTitle());
        description.setText(event.getDescription());
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(event.getTime());
        time.setText(reportDate);
        completed.setChecked(event.isCompleted());

        saveEvent.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.setCompleted(completed.isChecked());
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                intent.putExtra("eventDetail", event);
//                setResult(RESULT_OK, intent);
//                finish();
                dbHelper.modifyEvent(event);
                startActivity(intent);
            }
        });

        updateEvent.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EditEventDetail.class);
                intent.putExtra("eventDetail", event);
//                setResult(RESULT_OK, intent);
//                finish();
//                dbHelper.modifyEvent(event);
                startActivity(intent);
            }
        });

//        deleteEvent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dbHelper.removeEvent(event);
//                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(intent);
//            }
//        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
