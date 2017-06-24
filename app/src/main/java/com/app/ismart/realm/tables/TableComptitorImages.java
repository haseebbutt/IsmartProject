package com.app.ismart.realm.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Faheem-Abbas on 6/9/2017.
 */

public class TableComptitorImages extends RealmObject {
    @PrimaryKey
    private int id;
    private String shopid;
    private String beforeImage;
    private  String afterImage;
    private String date;
    private String displayid;

    public void setId(int id) {
        this.id = id;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public void setBeforeImage(String beforeImage) {
        this.beforeImage = beforeImage;
    }

    public void setAfterImage(String afterImage) {
        this.afterImage = afterImage;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDisplayid(String displayid) {
        this.displayid = displayid;
    }

    public int getId() {
        return id;
    }

    public String getShopid() {
        return shopid;
    }

    public String getBeforeImage() {
        return beforeImage;
    }

    public String getAfterImage() {
        return afterImage;
    }

    public String getDate() {
        return date;
    }

    public String getDisplayid() {
        return displayid;
    }
}
