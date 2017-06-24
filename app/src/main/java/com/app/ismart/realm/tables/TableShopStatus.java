package com.app.ismart.realm.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Faheem-Abbas on 5/23/2017.
 */

public class TableShopStatus extends RealmObject {

    @PrimaryKey
    private int id;
    private String shopId;
    private String location;
    private String date;
    private String status;
    private String reason;
    private String photo;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {

        this.location = location;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {

        return date;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {

        return status;
    }


    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getId() {

        return id;
    }

    public String getShopId() {
        return shopId;
    }

    public String getReason() {
        return reason;
    }

    public String getPhoto() {
        return photo;
    }
}
