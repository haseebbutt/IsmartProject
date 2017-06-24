package com.app.ismart.dto;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

/**
 * Created by HP 2 on 4/22/2017.
 */

public class CategoryDto implements Parent<ItemDto> {
    public int id;
    public String display;

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String mName;
    public List<ItemDto> mItems;

    public CategoryDto(String name, List<ItemDto> Items) {
        mName = name;
        mItems = Items;
    }

    public CategoryDto() {
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getName() {
        return mName;
    }

    @Override
    public List<ItemDto> getChildList() {
        return mItems;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public ItemDto getItem(int position) {
        return mItems.get(position);
    }


}
