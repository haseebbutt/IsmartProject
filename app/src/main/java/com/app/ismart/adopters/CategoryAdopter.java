package com.app.ismart.adopters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.ismart.R;
import com.app.ismart.databinding.CategoryItemBinding;
import com.app.ismart.dto.CategoryDto;
import com.app.ismart.dto.ItemDto;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.fragments.FragmentExpiredItems;
import com.app.ismart.rcvbase.BaseRecyclerViewAdapter;
import com.app.ismart.rcvbase.IOnItemClickListner;
import com.app.ismart.utils.FragmentUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Faheem-Abbas on 5/22/2017.
 */

public class CategoryAdopter extends BaseRecyclerViewAdapter<CategoryDto, CategoryItemBinding> implements IOnItemClickListner<CategoryDto> {
    HashMap<String, List<ItemDto>> expandableListDetail = new HashMap<String, List<ItemDto>>();
    ShopDto shopDto;

    public CategoryAdopter(List<CategoryDto> data, @NotNull Context context, HashMap<String, List<ItemDto>> expandableListDetail, ShopDto shopDto) {
        super(data, context);
        this.expandableListDetail = expandableListDetail;
        this.shopDto = shopDto;
    }

    @Override
    public void onRecyclerItemClick(CategoryDto model, View view, int position) {
        List<ItemDto> item = expandableListDetail.get(model.getName());
        FragmentExpiredItems fragment = new FragmentExpiredItems();
        fragment.item = item;
        fragment.shopDto=shopDto;
        new FragmentUtils((Activity) context, fragment, R.id.fragContainer);
    }

    @Override
    protected View onCreateViewHolderDynamic(Context context, LayoutInflater inflater, ViewGroup viewGroup, int viewType) {
        return inflater.inflate(R.layout.category_item, null);
    }

    @Override
    protected void onBindViewHolderDynamic(CategoryDto item, ViewHolder viewHolder, int position) {
        viewHolder.binding.txtCategoryName.setText(item.getName());

        setOnItemClickListner(this);
    }

    @Override
    protected void onViewHolderCreation(View itemView) {

    }

    @Override
    protected void onViewHolderCreated(ViewHolder viewHolder) {

    }
}
