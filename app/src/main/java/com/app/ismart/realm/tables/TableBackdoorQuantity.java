package com.app.ismart.realm.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Faheem-Abbas on 6/4/2017.
 */

public class TableBackdoorQuantity extends RealmObject {
    @PrimaryKey
    private int id;
    private String quantity;
    private  String shopId;
    private  String itemId;
    private  String date;
    private String timestamp;
    private String location;

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public String getQuantity() {
        return quantity;
    }

    public String getShopId() {
        return shopId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getDate() {
        return date;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
