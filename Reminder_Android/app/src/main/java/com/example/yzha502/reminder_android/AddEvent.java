package com.example.yzha502.reminder_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yzha502.reminder_android.models.DatabaseHelper;
import com.example.yzha502.reminder_android.models.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AddEvent extends Activity {

    private EditText title;
    private EditText description;
    private TextView date;
    private CalendarView time;
    private Button save;
    private Event event;
    private Date d;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        title=(EditText)findViewById(R.id.TitleEditText);
        description = (EditText)findViewById(R.id.descriptionEditText);
        date = (TextView) findViewById(R.id.Date);
        time=(CalendarView) findViewById(R.id.calendarView);
        save=(Button) findViewById(R.id.saveButton);

        Intent i = getIntent();
        id = (long)i.getIntExtra("eventsSize",0);
        id++;

        System.out.print(id);


        time.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO Auto-generated method stub

                date.setText(dayOfMonth+"/"+month+"/"+year);
                String date = year + "/" + month + "/" + dayOfMonth;


                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                    d = formatter.parse(date);

                } catch (Exception e) {
                    System.out.println(e.toString());
                    e.printStackTrace();
                }
            }
        });


        save.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event = new Event();
                event.setId(id);
                event.setCompleted(false);
                event.setTitle(title.getText().toString());
                event.setDescription(description.getText().toString());


                event.setTime(d);

                Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                dbHelper.addEvent(event);

                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_event, menu);
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
