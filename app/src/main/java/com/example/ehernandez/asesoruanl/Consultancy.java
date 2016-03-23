package com.example.ehernandez.asesoruanl;

/**
 * Created by ehernandez on 17/03/2016.
 */
public class Consultancy {
    private String name;
    private String summary;
    private String hour;
    private String objectId;

    public Consultancy(String name, String summary, String hour){
        this.name = name;
        this.summary = summary;
        this.hour = hour;
    }

    public Consultancy(String name, String summary, String hour, String objectId){
        this.name = name;
        this.summary = summary;
        this.hour = hour;
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
