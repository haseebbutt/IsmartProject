package com.app.ismart.fragments;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.app.ismart.MainActivity;
import com.app.ismart.R;
import com.app.ismart.adopters.DisplayAdopter;
import com.app.ismart.adopters.DisplayAdopterCompetitor;
import com.app.ismart.databinding.FragmentcomptitordisplayBinding;
import com.app.ismart.dto.CategoryDto;
import com.app.ismart.dto.CompetitorProductsDto;
import com.app.ismart.dto.CompetitorQuantityDto;
import com.app.ismart.dto.DisplayDto;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.interfaces.IonUpdateMark;
import com.app.ismart.realm.RealmController;
import com.app.ismart.realm.repository.ComptitorProductRepository;
import com.app.ismart.realm.repository.ComptitorQuantityRepository;
import com.app.ismart.realm.specfication.GetAllData;
import com.app.ismart.utils.FragmentUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Faheem-Abbas on 6/9/2017.
 */

public class FragmentComptitorDisplay  extends Fragment  {
    public ShopDto shopDto;
    static FragmentcomptitordisplayBinding layoutBinding;
    private DisplayAdopterCompetitor expandableListAdapter;
    HashMap<String, List<CategoryDto>> expandableListDetail = new HashMap<String, List<CategoryDto>>();
    List<CompetitorProductsDto> expandableListItemsDetail = new ArrayList<>();
    List<DisplayDto> displayList = new ArrayList<>();
    List<String> expandableListTitle;
    ArrayList<String> displays = new ArrayList<>();
    ProgressDialog progressdialog;
    private RealmController realmController;
    ComptitorProductRepository repository;
    ComptitorQuantityRepository quantityrepository;
    String date;
    public String location;
    String type = "backdoor";
    public IonUpdateMark ionUpdateMark;
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(ionUpdateMark!=null){
            ionUpdateMark.updateui();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragmentcomptitordisplay, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(layoutBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(shopDto.getName());
        layoutBinding.toolbar.setTitleTextColor(Color.WHITE);
        progressdialog = new ProgressDialog(getContext());
        progressdialog.setMessage("Loading please wait....");
        progressdialog.show();
        date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        realmController = RealmController.with(this);
        repository = new ComptitorProductRepository(realmController.getRealm());
        quantityrepository = new ComptitorQuantityRepository(realmController.getRealm());

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        List<CompetitorProductsDto> categories = repository.query(new GetAllData());
        if (categories.size() >= 1) {
            setAdopter(categories);
            progressdialog.dismiss();

        }
        layoutBinding.imgtakephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentComptitorImages fragmentComptitorImages= new FragmentComptitorImages();
                fragmentComptitorImages.shopDto = shopDto;
                fragmentComptitorImages.displayList=displayList;
                fragmentComptitorImages.list=displays;
                new FragmentUtils(getActivity(), fragmentComptitorImages, R.id.fragContainer);
            }
        });

        layoutBinding.expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String display = expandableListTitle.get(groupPosition);
                CategoryDto item = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);
                FragmentComptitorQuantity fragment = new FragmentComptitorQuantity();
                fragment.item = filterList(display, item.getName(), expandableListItemsDetail);
                fragment.shopDto = shopDto;
                new FragmentUtils(getActivity(), fragment, R.id.fragContainer);

                return false;
            }
        });


        return layoutBinding.getRoot();
    }

    public void setAdopter(List<CompetitorProductsDto> list) {
        expandableListDetail.clear();
        expandableListItemsDetail.clear();
        displays.clear();
        expandableListItemsDetail = list;
        for (CompetitorProductsDto itemDto : list) {
            if (!displays.contains(itemDto.getDisplay())) {
                displays.add(itemDto.getDisplay());
                DisplayDto displayDto=new DisplayDto();
                displayDto.display=itemDto.getDisplay();
                displayDto.displayid=itemDto.getDisplayId()+"";
                displayList.add(displayDto);
            }
        }
        for (String display : displays) {
            expandableListDetail.put(display, filterListBycategory(display, list));
        }

        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new DisplayAdopterCompetitor(getContext(), expandableListTitle, expandableListDetail);
        layoutBinding.expandableListView.setAdapter(expandableListAdapter);
    }

    public List<CompetitorProductsDto> filterList(String display, String category, List<CompetitorProductsDto> list) {
        List<CompetitorProductsDto> items = new ArrayList<>();
        for (CompetitorProductsDto itemDto : list) {
            if (itemDto.getCategory().equalsIgnoreCase(category) && itemDto.getDisplay().equalsIgnoreCase(display)) {
                items.add(itemDto);
                final List<CompetitorQuantityDto> itemquantity = quantityrepository.queryforitem(new GetAllData(), date, "" + shopDto.getId(), "" + itemDto.getId(),display,shopDto.getVisitId()+"");
                if (itemquantity.size() >= 1) {
                    itemDto.setQuantity(itemquantity.get(0).quantities);
                }

            }
        }
        return items;
    }

    public List<CategoryDto> filterListBycategory(String category, List<CompetitorProductsDto> list) {
        List<CategoryDto> items = new ArrayList<>();
        List<String> subitems = new ArrayList<>();
        for (CompetitorProductsDto itemDto : list) {
            if (itemDto.getDisplay().equalsIgnoreCase(category) && !subitems.contains(itemDto.getCategory())) {
                subitems.add(itemDto.getCategory());
                CategoryDto categoryDto = new CategoryDto();
                categoryDto.setmName(itemDto.getCategory());

                items.add(categoryDto);
            }
        }
        return items;
    }

    @Override
    public void onDestroyView() {
        expandableListDetail = null;
        expandableListTitle = null;
        ((MainActivity) getActivity()).enableshoplist(true);
        super.onDestroyView();

    }



    public static void enableClick(boolean enable) {
        layoutBinding.expandableListView.setEnabled(enable);
    }

}
