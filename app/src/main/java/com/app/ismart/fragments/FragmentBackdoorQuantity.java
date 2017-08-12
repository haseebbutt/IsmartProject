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
import com.app.ismart.adopters.TakeBackQuantityAdopter;
import com.app.ismart.adopters.TakeBackQuantityAdpt;
import com.app.ismart.adopters.TakeQuantityAdopter;
import com.app.ismart.databinding.FragmentBackdoorquantityBinding;
import com.app.ismart.dto.ItemDto;
import com.app.ismart.dto.QuantityDto;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.interfaces.OnEditTextChanged;
import com.app.ismart.interfaces.OnEditTextChangedNew;
import com.app.ismart.rcvbase.RecyclerViewUtils;
import com.app.ismart.realm.RealmController;
import com.app.ismart.realm.repository.BackdoorQuantityRepository;
import com.app.ismart.realm.repository.ProductRepository;
import com.app.ismart.realm.specfication.GetAllData;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Faheem-Abbas on 6/4/2017.
 */


public class FragmentBackdoorQuantity extends Fragment implements OnEditTextChangedNew {
    FragmentBackdoorquantityBinding layoutBinding;
    public List<ItemDto> item;
    String [] item2;
    String date;
    public ShopDto shopDto;
    private RealmController realmController;
    ProductRepository repository;
    BackdoorQuantityRepository backdoorQuantityRepository;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_backdoorquantity, container, false);

        ((AppCompatActivity) getActivity()).setSupportActionBar(layoutBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Categories");
        layoutBinding.toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     //   TakeBackQuantityAdopter adapter = new TakeBackQuantityAdopter(item, getContext(), this,shopDto);
        int SIZE=item.size();

        //  Toast.makeText(getActivity(),""+SIZE,Toast.LENGTH_LONG).show();
        item2=new String[SIZE];

        TakeBackQuantityAdpt adapter=new TakeBackQuantityAdpt(item,item2,getContext(),shopDto,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        realmController = RealmController.with(this);
        repository = new ProductRepository(realmController.getRealm());
        backdoorQuantityRepository = new BackdoorQuantityRepository(realmController.getRealm());
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
                    for (ItemDto itemDto : item) {

                        try {
                            final List<QuantityDto> itemquantity = backdoorQuantityRepository.queryforitem(new GetAllData(), date, "" + shopDto.getId(), "" + itemDto.getId(), shopDto.getVisitId() + "");
                            // Toast.makeText(getContext(), ""+itemDto.getTitle()+"\n"+itemDto.getQuantity(), Toast.LENGTH_SHORT).show();
                            QuantityDto dto = new QuantityDto();
                            dto.quantity = itemDto.getQuantity();
                            dto.shopid = "" + shopDto.getId();
                            dto.itemid = "" + itemDto.getId();
                            dto.date = date;
                            dto.location = location;
                            dto.timestamp = getDateTime();
                            dto.visitid = shopDto.getVisitId() + "";
                            if (itemquantity.size() >= 1) {
                                dto.id = itemquantity.get(0).id;
                                backdoorQuantityRepository.update(dto);
                                //  Toast.makeText(getContext(), "Quantity Updated", Toast.LENGTH_SHORT).show();
                            } else {
                                backdoorQuantityRepository.add(dto);
                                //  Toast.makeText(getContext(), "Quantity Added", Toast.LENGTH_SHORT).show();
                            }
                        }catch(Exception e){


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
    public void onTextChanged1(int position, String charSeq) {
        item.get(position).setQuantity(charSeq);
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
