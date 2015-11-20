package com.example.yzha502.reminder_android.models;



import android.app.usage.UsageEvents;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by yzha502 on 25/04/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MonsterDB";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Event.CREATESTATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Event.TABLE_NAME);
    }

    public void addEvent(Event e) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Event.COLUMN_ID, e.getId());
        values.put(Event.COLUMN_TITLE, e.getTitle());
        values.put(Event.COLUMN_DESCRIPTION, e.getDescription());
        values.put(Event.COLUMN_COMPLETED, e.isCompleted());
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = format.format(e.getTime());
        values.put(Event.COLUMN_TIME, reportDate);
        db.insert(Event.TABLE_NAME, null, values);
        db.close();
    }

    public HashMap<Long, Event> getAllEvents() {
        HashMap<Long, Event> monsters = new LinkedHashMap<Long, Event>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Event.TABLE_NAME, null);
// Add monster to hash map for each row result
        if(cursor.moveToFirst()) { do {
            try{
            String date = cursor.getString(3);

            DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            Date da = format.parse(date);
            int i = cursor.getInt(4);
            boolean b;
            if (i==0)
                b = false;
            else
                b=true;

            Event m = new Event(cursor.getLong(0), cursor.getString(1), cursor.getString(2), da, b);
            monsters.put(m.getId(), m);
            }catch(Exception e)
            {System.out.print(e.getMessage());}
        }
            while(cursor.moveToNext());

        }
// Close cursor
        cursor.close();

        return monsters; }

    public void removeEvent(Event e) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Event.TABLE_NAME,
                Event.COLUMN_ID + " = ?",
                new String[]{String.valueOf(e.getId())});
        db.close(); }

    public void modifyEvent(Event e) {
        SQLiteDatabase db = this.getWritableDatabase();

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(e.getTime());

        int i;
        if (e.isCompleted()==false)
            i = 0;
        else
            i=1;

        ContentValues cv = new ContentValues();
        cv.put("title",e.getTitle()); //These Fields should be your String values of actual column names
        cv.put("description",e.getDescription());
        cv.put("date",reportDate);
        cv.put("completed",i);

        String id = String.valueOf(e.getId());
        db.update(Event.TABLE_NAME,cv,"id="+e.getId(),null);
        db.close(); }



}
