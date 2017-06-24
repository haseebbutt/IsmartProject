package com.app.ismart.realm.tables;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Faheem-Abbas on 5/14/2017.
 */

public class TableShops extends RealmObject {

    @PrimaryKey
    private int id;
    private String shopId;
    private String name;
    private String address;

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopId() {

        return shopId;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
