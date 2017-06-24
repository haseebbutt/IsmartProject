package com.app.ismart.realm.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Faheem-Abbas on 5/25/2017.
 */

public class TableFeedback extends RealmObject {
    @PrimaryKey
    private int id;

    private String feedbackid;

    private String feedback;

    private String categoryid;

    private String category;



    public int getId() {
        return id;
    }

    public String getFeedbackid() {
        return feedbackid;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public String getCategory() {
        return category;
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

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
