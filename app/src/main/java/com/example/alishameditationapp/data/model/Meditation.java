package com.example.alishameditationapp.data.model;

import java.util.ArrayList;

public class Meditation {

    public ArrayList<MeditationData> meditationData;


    public Meditation() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Meditation(ArrayList<MeditationData> arrayList) {
        this.meditationData = arrayList;
    }

    public void setMeditationData(ArrayList<MeditationData> meditationData) {
        this.meditationData = meditationData;
    }

    public ArrayList<MeditationData> getMeditationData() {
        return meditationData;
    }
}