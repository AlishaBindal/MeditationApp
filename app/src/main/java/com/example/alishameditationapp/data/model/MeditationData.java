package com.example.alishameditationapp.data.model;

public class MeditationData {

    private DataField dataField;

    private String documentID;

    private SubCollection subCollection;


    public DataField getDataField() {
        return dataField;
    }

    public void setDataField(DataField dataField) {
        this.dataField = dataField;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public SubCollection getSubCollection() {
        return subCollection;
    }

    public void setSubCollection(SubCollection subCollection) {
        this.subCollection = subCollection;
    }

}