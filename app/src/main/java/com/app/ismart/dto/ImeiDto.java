package com.app.ismart.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP on 6/20/2017.
 */

public class ImeiDto {
    @SerializedName("id")
    @Expose
    private int id;

    private String imei;

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImei(){

        return imei;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getId() {

        return id;
    }
}
