package com.example.ehernandez.asesoruanl;

/**
 * Created by ehernandez on 17/03/2016.
 */
public class Consultancy {
    private String name;
    private String summary;
    private String hour;
    private String objectId;
    private String email;
    private String consultantsNumber;

    public Consultancy(String name, String summary, String hour, String objectId, String email){
        this.name = name;
        this.summary = summary;
        this.hour = hour;
        this.objectId = objectId;
        this.email = email;
    }

    public Consultancy(String name, String summary, String hour, String objectId){
        this.name = name;
        this.summary = summary;
        this.hour = hour;
        this.objectId = objectId;
    }

    public Consultancy(String summary, String consultantsNumber){
        this.summary = summary;
        this.consultantsNumber = consultantsNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public String getConsultantsNumber() {
        return consultantsNumber;
    }

    public void setConsultantsNumber(String consultantsNumber) {
        this.consultantsNumber = consultantsNumber;
    }
}
