package com.example.yzha502.reminder_android.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by yzha502 on 25/04/15.
 */
public class Event implements Parcelable {

    private long id;
    private String title;
    private String description;
    private Date time;
    private boolean completed;

    public static final String TABLE_NAME = "evnets";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TIME = "date";
    public static final String COLUMN_COMPLETED = "completed";

    public static final String CREATESTATEMENT= "Create Table " +TABLE_NAME+"( "
            +COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUMN_TITLE + " TEXT NOT NULL, " +
            COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
            COLUMN_TIME + " TEXT NOT NULL, " +
            COLUMN_COMPLETED +" INTEGER NOT NULL)";


    public Event() {
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }
        public Event[] newArray(int size) {
            return new Event[size]; }
    };

    public Event(long id, String title, String description, Date time, boolean completed) {
        this.id=id;
        this.title=title;
        this.description=description;
        this.time=time;
        this.completed = completed;
    }

    public Event(Parcel in) {
        this.id=in.readLong();
        this.title = in.readString();
        this.description = in.readString();
        this.time = (Date) in.readSerializable();
        this.completed=in.readByte() != 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeSerializable(time);
        dest.writeByte((byte) (completed ? 1 : 0));
    }
}
