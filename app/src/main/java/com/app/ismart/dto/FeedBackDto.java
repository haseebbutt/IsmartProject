package com.app.ismart.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Faheem-Abbas on 5/25/2017.
 */


public class FeedBackDto {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("feedback")
    @Expose
    private String feedback;
    @SerializedName("category_id")
    @Expose
    private Integer category_id;
    @SerializedName("category")
    @Expose
    private String category;
    private String answers = "";
    private String feedbackid;

    public Integer getCategory_id() {
        return category_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setFeedbackid(String feedbackid) {
        this.feedbackid = feedbackid;
    }

    public String getFeedbackid() {
        return feedbackid;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }


}