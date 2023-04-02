package com.example.appforelderlyprotoelec;
import androidx.room.PrimaryKey;

public class Schedule {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String day;
    private String time;
    private String activity;

    public Schedule() {}

    public Schedule(String day, String time, String activity) {
        this.day = day;
        this.time = time;
        this.activity = activity;
        setId(id);
    }

    public int getId() {
        System.out.println(id);
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
