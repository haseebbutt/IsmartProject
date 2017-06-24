package com.app.ismart.realm.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Faheem-Abbas on 5/14/2017.
 */

public class TableProducts extends RealmObject {
    @PrimaryKey
    private int id;
    private int itemId;
    private String title;
    private String display;
    private String shopid;
    private String imageurl;

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {

        return id;
    }

    public int getItemId() {
        return itemId;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    private String category;
}
