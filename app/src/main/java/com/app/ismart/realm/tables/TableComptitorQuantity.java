package com.app.ismart.realm.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Faheem-Abbas on 6/9/2017.
 */

public class TableComptitorQuantity extends RealmObject {
    @PrimaryKey
    private int id;
    private String shopId;
    private String products;
    private String quantities;
    private String date;
    private String timestamp;
    private String location;
    private String displayId;
    private String display;

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public void setQuantities(String quantities) {
        this.quantities = quantities;
    }


    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }

    public int getId() {
        return id;
    }

    public String getShopId() {
        return shopId;
    }

    public String getProducts() {
        return products;
    }

    public String getQuantities() {
        return quantities;
    }


    public String getDisplayId() {
        return displayId;
    }
}
