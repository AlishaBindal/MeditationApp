package com.example.alishameditationapp.data.model;

public class CollectionData {
    private String imageLink;
    private String link;

    public CollectionData(String imageLink, String link) {
        this.imageLink = imageLink;
        this.link = link;
    }

    public CollectionData() {
    }


    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
