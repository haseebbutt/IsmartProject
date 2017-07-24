package com.app.ismart.realm.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Faheem-Abbas on 5/25/2017.
 */

public class TableFeedbackSubmit extends RealmObject {

    @PrimaryKey
    private int id;

    private String feedbackid;

    private String feedback;

    private String shopid;

    private String location;

    private String visitid;
private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFeedbackid(String feedbackid) {
        this.feedbackid = feedbackid;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public void setVisitid(String visitid) {
        this.visitid = visitid;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getFeedbackid() {
        return feedbackid;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getShopid() {
        return shopid;
    }

    public String getVisitid() {
        return visitid;
    }

    public String getLocation() {
        return location;
    }
}
