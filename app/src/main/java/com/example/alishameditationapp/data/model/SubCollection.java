package com.example.alishameditationapp.data.model;

public class SubCollection {
    private String collectionID;

    private CollectionData collectionData;

    private String documentID;

    public SubCollection(String collectionID, CollectionData collectionData, String documentID) {
        this.collectionID = collectionID;
        this.collectionData = collectionData;
        this.documentID = documentID;
    }

    public SubCollection() {

    }


    public String getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(String collectionID) {
        this.collectionID = collectionID;
    }

    public CollectionData getCollectionData() {
        return collectionData;
    }

    public void setCollectionData(CollectionData dataField) {
        this.collectionData = dataField;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }
}
