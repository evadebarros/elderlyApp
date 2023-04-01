package com.example.appforelderlyprotoelec;

import java.time.LocalTime;

public class DayTime {
    private String day, activity;
    private LocalTime time;

    public DayTime(String day, LocalTime time, String activity) {
        this.day = day;
        this.time = time;
        this.activity=activity;
    }

    public String getDay() {
        return day;
    }

    public String getActivity(){
        return activity;
    }
    public LocalTime getTime() {
        return time;
    }
}