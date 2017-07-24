package com.app.ismart.realm.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Faheem-Abbas on 5/29/2017.
 */

public class TablePopSubmit extends RealmObject {
    @PrimaryKey
    private int id;
    private String popid;
    private String shopid;
    private String quantity;
    private String photo;
    private String date;
    private String timestamp;
    private String location;
    private String visitid;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPopid(String popid) {
        this.popid = popid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public void setVisitid(String visitid) {
        this.visitid = visitid;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getPopid() {
        return popid;
    }

    public String getShopid() {
        return shopid;
    }

    public String getVisitid() {
        return visitid;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDate() {
        return date;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
