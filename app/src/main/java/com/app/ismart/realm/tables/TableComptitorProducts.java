package com.app.ismart.realm.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Faheem-Abbas on 6/9/2017.
 */

public class TableComptitorProducts extends RealmObject{
    @PrimaryKey
    private int id;
    private String displayId;

    private String display;

    private String categoryId;

    private String category;

    private String title;

    private String productid;



    public void setId(int id) {
        this.id = id;
    }

    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public int getId() {
        return id;
    }

    public String getDisplayId() {
        return displayId;
    }

    public String getDisplay() {
        return display;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getProductid() {
        return productid;
    }
}
