package com.app.ismart.realm.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Faheem-Abbas on 5/14/2017.
 */

public class TablesQuantity extends RealmObject {
    @PrimaryKey
    private int id;
    private String quantity;
    private  String shopId;
    private  String itemId;
    private  String display;
    private  String date;

    public String getdisplay() {
        return display;
    }

    public void setdisplay(String display) {
        this.display = display;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {

        return date;
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


}
