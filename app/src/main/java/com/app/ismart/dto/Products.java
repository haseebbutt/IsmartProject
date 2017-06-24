package com.app.ismart.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Faheem-Abbas on 5/30/2017.
 */

public class Products {


    @SerializedName("displayId")
    @Expose
    private Integer displayId;
    @SerializedName("display")
    @Expose
    private String display;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("plenogram")
    @Expose
    private String plenogram;

    public String getPlenogram() {
        return plenogram;
    }

    public void setPlenogram(String plenogram) {
        this.plenogram = plenogram;
    }

    public Integer getDisplayId() {
        return displayId;
    }

    public void setDisplayId(Integer displayId) {
        this.displayId = displayId;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}