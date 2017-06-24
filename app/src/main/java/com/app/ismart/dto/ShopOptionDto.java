package com.app.ismart.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Faheem-Abbas on 6/6/2017.
 */

public class ShopOptionDto {
    private int icon=0;
    private boolean isFilled=false;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }
}
