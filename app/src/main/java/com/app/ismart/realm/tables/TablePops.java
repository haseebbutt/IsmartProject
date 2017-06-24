package com.app.ismart.realm.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Faheem-Abbas on 5/28/2017.
 */

public class TablePops extends RealmObject {

    @PrimaryKey
    private int id;
    private int popId;

    private String name;

    private String createdAt;

    private String updatedAt;

    public void setId(int id) {
        this.id = id;
    }

    public void setPopId(int popId) {
        this.popId = popId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public int getPopId() {
        return popId;
    }

    public String getName() {
        return name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
