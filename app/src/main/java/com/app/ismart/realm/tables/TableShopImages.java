package com.app.ismart.realm.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Faheem-Abbas on 5/19/2017.
 */

public class TableShopImages extends RealmObject {
    @PrimaryKey
    private int id;
    private String beforeimage;
    private  String afterImage;
    private  String shopid;
    private  String date;

    public void setId(int id) {
        this.id = id;
    }

    public void setBeforeimage(String beforeimage) {
        this.beforeimage = beforeimage;
    }

    public void setAfterImage(String afterImage) {
        this.afterImage = afterImage;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {

        return id;
    }

    public String getBeforeimage() {
        return beforeimage;
    }

    public String getAfterImage() {
        return afterImage;
    }

    public String getShopid() {
        return shopid;
    }

    public String getDate() {
        return date;
    }
}