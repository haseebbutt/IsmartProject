package com.app.ismart.realm.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Faheem-Abbas on 5/14/2017.
 */

public class TableCategory extends RealmObject {
    @PrimaryKey
    private int id;
    private String CategoryName;
    private String display;

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getId() {

        return id;
    }

    public String getCategoryName() {
        return CategoryName;
    }
}
