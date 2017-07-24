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
import com.app.ismart.adopters.ComptitorQuantityAdopter;
import com.app.ismart.databinding.FragmentcomptitorquantityBinding;
import com.app.ismart.dto.CompetitorProductsDto;
import com.app.ismart.dto.CompetitorQuantityDto;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.interfaces.OnEditTextChanged;
import com.app.ismart.rcvbase.RecyclerViewUtils;
import com.app.ismart.realm.RealmController;
import com.app.ismart.realm.repository.ComptitorProductRepository;
import com.app.ismart.realm.repository.ComptitorQuantityRepository;
import com.app.ismart.realm.specfication.GetAllData;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Faheem-Abbas on 6/9/2017.
 */

public class FragmentComptitorQuantity extends Fragment implements OnEditTextChanged {
    FragmentcomptitorquantityBinding layoutBinding;
    public List<CompetitorProductsDto> item;
    String date;
    public ShopDto shopDto;
    private RealmController realmController;
    ComptitorProductRepository repository;
    ComptitorQuantityRepository quantityrepository;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragmentcomptitorquantity, container, false);

        ((AppCompatActivity) getActivity()).setSupportActionBar(layoutBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Categories");
        layoutBinding.toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ComptitorQuantityAdopter adapter = new ComptitorQuantityAdopter(item, getContext(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        realmController = RealmController.with(this);
        repository = new ComptitorProductRepository(realmController.getRealm());
        quantityrepository = new ComptitorQuantityRepository(realmController.getRealm());
        date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        layoutBinding.rcvitems.setLayoutManager(RecyclerViewUtils.getGridLayoutManager(getContext(), 1));
        layoutBinding.rcvitems.setItemAnimator(new DefaultItemAnimator());
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
                    for (CompetitorProductsDto itemDto : item) {
                        final List<CompetitorQuantityDto> itemquantity = quantityrepository.queryforitem(new GetAllData(), date, "" + shopDto.getId(), "" + itemDto.getId(),itemDto.getDisplay(),shopDto.getVisitId()+"");
                        // Toast.makeText(getContext(), ""+itemDto.getTitle()+"\n"+itemDto.getQuantity(), Toast.LENGTH_SHORT).show();
                        CompetitorQuantityDto dto = new CompetitorQuantityDto();
                        dto.quantities = itemDto.getQuantity();
                        dto.shopId = "" + shopDto.getId();
                        dto.products = "" + itemDto.getId();
                        dto.date = date;
                        dto.location = location;
                        dto.displayId = itemDto.getDisplayId() + "";
                        dto.display=itemDto.getDisplay();
                        dto.timestamp = getDateTime();
                        dto.visitId=shopDto.getVisitId()+"";
                        if (itemquantity.size() >= 1) {
                            dto.id = itemquantity.get(0).id;
                            quantityrepository.update(dto);
                            //  Toast.makeText(getContext(), "Quantity Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            quantityrepository.add(dto);
                            //  Toast.makeText(getContext(), "Quantity Added", Toast.LENGTH_SHORT).show();
                        }

                    }
                    Toast.makeText(getContext(), "Quantity saved", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
        return layoutBinding.getRoot();
    }

    @Override
    public void onTextChanged(int position, String charSeq) {
        item.get(position).setQuantity(charSeq);
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}

