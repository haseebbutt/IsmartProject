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

import com.app.ismart.MainActivity;
import com.app.ismart.R;
import com.app.ismart.adopters.FeedbackCategoryAdopter;
import com.app.ismart.databinding.FragmentfeedbackcategoryBinding;
import com.app.ismart.dto.CategoryDto;
import com.app.ismart.dto.FeedBackDto;
import com.app.ismart.dto.FeedbackSubmitDto;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.interfaces.IonUpdateMark;
import com.app.ismart.rcvbase.RecyclerViewUtils;
import com.app.ismart.realm.RealmController;
import com.app.ismart.realm.repository.FeedbackRepository;
import com.app.ismart.realm.repository.FeedbackSubmitRepository;
import com.app.ismart.realm.specfication.GetAllData;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Faheem-Abbas on 6/7/2017.
 */

public class FragmentFeedbackCategory extends Fragment {
    public ShopDto shopDto;
    static FragmentfeedbackcategoryBinding layoutBinding;
    HashMap<String, List<FeedBackDto>> expandableListDetail = new HashMap<String, List<FeedBackDto>>();
    List<CategoryDto> expandableListTitle = new ArrayList<>();
    ArrayList<String> categories = new ArrayList<>();
    private RealmController realmController;
    FeedbackRepository repository;
    FeedbackSubmitRepository quantityrepository;
    String date;
    public String location;
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
        layoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragmentfeedbackcategory, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(layoutBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(shopDto.getName());
        layoutBinding.toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        realmController = RealmController.with(this);
        repository = new FeedbackRepository(realmController.getRealm());
        quantityrepository = new FeedbackSubmitRepository(realmController.getRealm());
        List<FeedBackDto> categories = repository.query(new GetAllData());
        if (categories.size() >= 1) {
            setAdopter(categories);
        }

       return layoutBinding.getRoot();
    }

    public void setAdopter(List<FeedBackDto> list) {
        expandableListDetail.clear();
        categories.clear();

        for (FeedBackDto itemDto : list) {
            if (!categories.contains(itemDto.getCategory())) {
                categories.add(itemDto.getCategory());
                CategoryDto categoryDto = new CategoryDto();
                categoryDto.setmName(itemDto.getCategory());
                expandableListTitle.add(categoryDto);
            }
        }
        for (String category : categories) {
            expandableListDetail.put(category, filterList(category, list));
        }

        FeedbackCategoryAdopter adapter = new FeedbackCategoryAdopter(expandableListTitle, getContext(), expandableListDetail, shopDto);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutBinding.rcvcategory.setLayoutManager(RecyclerViewUtils.getGridLayoutManager(getContext(), 1));
        layoutBinding.rcvcategory.setItemAnimator(new DefaultItemAnimator());
        layoutBinding.rcvcategory.setAdapter(adapter);
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        layoutBinding.rcvcategory.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext()).paint(paint).build());
        adapter.notifyDataSetChanged();

    }

    public List<FeedBackDto> filterList(String category, List<FeedBackDto> list) {
        List<FeedBackDto> items = new ArrayList<>();
        List<FeedbackSubmitDto> feedbacks=quantityrepository.query(new GetAllData());
        for (FeedBackDto itemDto : list) {
            if (itemDto.getCategory().equalsIgnoreCase(category)) {
                final List<FeedbackSubmitDto> itemquantity =quantityrepository .queryforfeedback(new GetAllData(), shopDto.getId()+"", "" + itemDto.getFeedbackid(),""+shopDto.getVisitId());
                if (itemquantity.size() >= 1) {
                    itemDto.setAnswers(itemquantity.get(0).response);
                }
                items.add(itemDto);
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
        layoutBinding.rcvcategory.setEnabled(enable);
    }
}