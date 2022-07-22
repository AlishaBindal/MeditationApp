package com.example.alishameditationapp.data.model;

public class DataField {
    private int doingRightNow;
    private String name;


    public DataField() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    public DataField(int doingRightNow, String name) {
        this.doingRightNow = doingRightNow;
        this.name = name;
    }

    public int getDoingRightNow() {
        return doingRightNow;
    }

    public void setDoingRightNow(int doingRightNow) {
        this.doingRightNow = doingRightNow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
