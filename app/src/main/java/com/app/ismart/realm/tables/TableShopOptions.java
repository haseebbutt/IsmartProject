package com.app.ismart.realm.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Faheem-Abbas on 6/6/2017.
 */

public class TableShopOptions extends RealmObject {
    @PrimaryKey
    private int id;
    private int componentId;
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getComponentId() {
        return componentId;
    }

    public String getName() {
        return name;
    }
}
