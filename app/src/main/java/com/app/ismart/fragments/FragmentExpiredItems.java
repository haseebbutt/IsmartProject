package com.app.ismart.fragments;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.ismart.MainActivity;
import com.app.ismart.R;
import com.app.ismart.adopters.ExpiredItemsAdopter;
import com.app.ismart.databinding.FragmentExpiredBinding;
import com.app.ismart.dto.ExpiredItemDto;
import com.app.ismart.dto.ItemDto;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.interfaces.IOnExpired;
import com.app.ismart.rcvbase.RecyclerViewUtils;
import com.app.ismart.realm.RealmController;
import com.app.ismart.realm.repository.ExpiredItemRepository;
import com.app.ismart.realm.repository.ProductRepository;
import com.app.ismart.realm.repository.QuanityRepository;
import com.app.ismart.realm.specfication.GetAllData;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Faheem-Abbas on 6/2/2017.
 */

public class FragmentExpiredItems extends Fragment implements IOnExpired {
    FragmentExpiredBinding layoutBinding;
    public List<ItemDto> item;
    public List<ExpiredItemDto> expiredItem = new ArrayList<>();
    String date;
    public ShopDto shopDto;
    private RealmController realmController;
    ProductRepository repository;
    QuanityRepository quantityrepository;
    ExpiredItemRepository expiredItemRepository;
    ArrayList<String> itemid=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_expired, container, false);

        ((AppCompatActivity) getActivity()).setSupportActionBar(layoutBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Expired Items");
        layoutBinding.toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        realmController = RealmController.with(this);
        repository = new ProductRepository(realmController.getRealm());
        quantityrepository = new QuanityRepository(realmController.getRealm());
        expiredItemRepository = new ExpiredItemRepository(realmController.getRealm());
        date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        layoutBinding.rcvitems.setLayoutManager(RecyclerViewUtils.getGridLayoutManager(getContext(), 1));
        layoutBinding.rcvitems.setItemAnimator(new DefaultItemAnimator());
        for (ItemDto itemDto : item) {
            if(!itemid.contains(itemDto.getId()+"")) {
                ExpiredItemDto expiredItemDto = new ExpiredItemDto();
                expiredItemDto.itemid = itemDto.getId() + "";
                expiredItemDto.itemname = itemDto.getTitle();
                expiredItemDto.shopid = shopDto.getId() + "";
                expiredItemDto.date = date;
                expiredItemDto.visitid=""+shopDto.getVisitId();
                List<ExpiredItemDto> exists = expiredItemRepository.queryforitem(new GetAllData(), date, shopDto.getId() + "", itemDto.getId() + "",shopDto.getVisitId()+"");
                if (exists.size() >= 1) {
                    expiredItemDto.expired = exists.get(0).expired;
                    expiredItemDto.nearexpired = exists.get(0).nearexpired;
                }
                expiredItem.add(expiredItemDto);
                itemid.add(itemDto.getId()+"");
            }
        }
        ExpiredItemsAdopter adapter = new ExpiredItemsAdopter(expiredItem, getContext(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutBinding.rcvitems.setAdapter(adapter);
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        layoutBinding.rcvitems.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext()).paint(paint).build());
        adapter.notifyDataSetChanged();

        layoutBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = ((MainActivity) getActivity()).checklocation();
                if (location != null) {
                    for (ExpiredItemDto itemDto : expiredItem) {
                        List<ExpiredItemDto> exists = expiredItemRepository.queryforitem(new GetAllData(), date, shopDto.getId() + "", itemDto.itemid + "",shopDto.getVisitId()+"");
                        // Toast.makeText(getContext(), ""+itemDto.getTitle()+"\n"+itemDto.getQuantity(), Toast.LENGTH_SHORT).show();
                        itemDto.location = location;
                        itemDto.timestamp = getDateTime();
                        if (exists.size() >= 1) {

                            expiredItemRepository.update(itemDto);
                            //  Toast.makeText(getContext(), "Quantity Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            expiredItemRepository.add(itemDto);
                            //  Toast.makeText(getContext(), "Quantity Added", Toast.LENGTH_SHORT).show();
                        }

                    }
                    Toast.makeText(getContext(), "Expired items saved", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
        return layoutBinding.getRoot();
    }

    @Override
    public void onTextChanged(int type, int position, String charSeq) {
        switch (type) {
            case 1:
                expiredItem.get(position).expired = charSeq;
                break;
            case 2:
                expiredItem.get(position).nearexpired = charSeq;
                break;

        }

    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
