package com.app.ismart.fragments;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.ismart.R;
import com.app.ismart.adopters.DisplayImagesAdopter;
import com.app.ismart.databinding.FragmentimagesBinding;
import com.app.ismart.dto.DisplayDto;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.rcvbase.RecyclerViewUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by Faheem-Abbas on 6/10/2017.
 */

public class FragmentImages extends Fragment {

    FragmentimagesBinding layoutBinding;
    public ShopDto shopDto;
    public ArrayList<DisplayDto> display;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragmentimages, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(layoutBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(shopDto.getName());
        layoutBinding.toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        DisplayImagesAdopter adapter = new DisplayImagesAdopter(display, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutBinding.rcvimages.setLayoutManager(RecyclerViewUtils.getGridLayoutManager(getContext(), 1));
        layoutBinding.rcvimages.setItemAnimator(new DefaultItemAnimator());
        layoutBinding.rcvimages.setAdapter(adapter);
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        layoutBinding.rcvimages.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext()).paint(paint).build());
        adapter.notifyDataSetChanged();
        return layoutBinding.getRoot();
    }

}
