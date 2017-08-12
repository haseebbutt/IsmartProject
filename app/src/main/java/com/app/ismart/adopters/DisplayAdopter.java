package com.app.ismart.adopters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ismart.R;
import com.app.ismart.dto.CategoryDto;
import com.app.ismart.dto.DisplayDto;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.fragments.FragmentChecking;
import com.app.ismart.fragments.FragmentImages;
import com.app.ismart.utils.FragmentUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by HP 2 on 4/22/2017.
 */

public class DisplayAdopter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<CategoryDto>> expandableListDetail;
    ImageView capture;
    ImageView planogram;
    public ShopDto shopDto;
    ArrayList<DisplayDto> displaylist = new ArrayList<>();
    int position;

    public DisplayAdopter(Context context, List<String> expandableListTitle,
                          HashMap<String, List<CategoryDto>> expandableListDetail,ArrayList<DisplayDto> displaylist,ShopDto shopDto) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.displaylist=displaylist;
        this.shopDto=shopDto;
    }


    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final CategoryDto item = (CategoryDto) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.txtItemName);
        expandedListTextView.setText(item.getName());
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);

        position=listPosition;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.category_item1, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.txtCategoryName);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);

         capture=(ImageView) convertView.findViewById(R.id.imgtakephoto);
         planogram=(ImageView) convertView.findViewById(R.id.imgdisplayimages);
      //  capture=new ImageView((Activity)context);

        capture.setTag(listPosition);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // String displayId=(String)displaylist.get(position).display;
             //   Toast.makeText(context,""+ v.getTag(),Toast.LENGTH_LONG).show();

                FragmentChecking fragmentChecking = new FragmentChecking();
                fragmentChecking.shopDto = shopDto;
               // fragmentChecking.display=displaylist;
               // fragmentChecking.pos=""+v.getTag();
                new FragmentUtils((Activity) context, fragmentChecking, R.id.fragContainer);
            }
        });

        planogram.setTag(listPosition);
        planogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   Toast.makeText(context,""+ v.getTag(),Toast.LENGTH_LONG).show();
                FragmentImages fragmentImages = new FragmentImages();
                fragmentImages.shopDto = shopDto;
                fragmentImages.display=displaylist;
                new FragmentUtils((Activity) context, fragmentImages, R.id.fragContainer);
            }
        });

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
