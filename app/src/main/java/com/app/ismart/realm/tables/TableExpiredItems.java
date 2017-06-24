package com.app.ismart.realm.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Faheem-Abbas on 6/3/2017.
 */

public class TableExpiredItems extends RealmObject {
    @PrimaryKey
    private int id;
    private String expired;
    private String shopId;
    private String itemId;
    private String timestamp;
    private String location;
    private String nearexpired;
    private String date;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNearexpired(String nearexpired) {
        this.nearexpired = nearexpired;
    }

    public int getId() {
        return id;
    }

    public String getExpired() {
        return expired;
    }

    public String getShopId() {
        return shopId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getLocation() {
        return location;
    }

    public String getNearexpired() {
        return nearexpired;
    }
}
