package com.app.ismart.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP 2 on 4/22/2017.
 */

public class ShopDto {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("day")
    @Expose
    private String day;

    @SerializedName("visitid")
    @Expose
    private int visitId;

    @SerializedName("drive_id")
    @Expose
    private int drive_id;


    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setDrive_id(int drive_id) {
        this.drive_id = drive_id;
    }



    public int getId() {
        return id;
    }
    public int getVisitId() {
        return visitId;
    }

    public int getDrive_id() {
        return drive_id;
    }

    public String getName() {
        return name;
    }

    public String getDay() {
        return day;
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

    public void setDay(String day) {
        this.day = day;
    }
}
