package com.app.ismart.fragments;

import android.app.Activity;
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

import com.app.ismart.R;
import com.app.ismart.adopters.TakeQuantityAdopter;
import com.app.ismart.adopters.TakeQuantityAdpt;
import com.app.ismart.databinding.FragmenttakequantityBinding;
import com.app.ismart.dto.DisplayDto;
import com.app.ismart.dto.ItemDto;
import com.app.ismart.dto.QuantityDto;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.interfaces.IOnExpiredNew;
import com.app.ismart.interfaces.OnEditTextChanged;
import com.app.ismart.interfaces.OnEditTextChangedNew;
import com.app.ismart.rcvbase.RecyclerViewUtils;
import com.app.ismart.realm.RealmController;
import com.app.ismart.realm.repository.ProductRepository;
import com.app.ismart.realm.repository.QuanityRepository;
import com.app.ismart.realm.specfication.GetAllData;
import com.app.ismart.utils.FragmentUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Faheem-Abbas on 5/22/2017.
 */

public class FragmentTakeQuantity extends Fragment implements IOnExpiredNew {
    FragmenttakequantityBinding layoutBinding;
    public List<ItemDto> item;
    String [] item2;
    String [] item3;
    String date;
    public ShopDto shopDto;
    private RealmController realmController;
    ProductRepository repository;
    String display;
    public ArrayList<DisplayDto> displaylist;

    QuanityRepository quantityrepository;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragmenttakequantity, container, false);

        ((AppCompatActivity) getActivity()).setSupportActionBar(layoutBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Categories");
        layoutBinding.toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // TakeQuantityAdopter adapter = new TakeQuantityAdopter(item, getContext(), this);

        int SIZE=item.size();

      //  Toast.makeText(getActivity(),""+SIZE,Toast.LENGTH_LONG).show();
        item2=new String[SIZE];
        item3=new String[SIZE];

        TakeQuantityAdpt adpt=new TakeQuantityAdpt(item,item2,item3,this,getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        realmController = RealmController.with(this);
        repository = new ProductRepository(realmController.getRealm());
        quantityrepository = new QuanityRepository(realmController.getRealm());
        date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        layoutBinding.rcvitems.setLayoutManager(RecyclerViewUtils.getGridLayoutManager(getContext(), 1));
        layoutBinding.rcvitems.setItemAnimator(new DefaultItemAnimator());
        layoutBinding.rcvitems.setAdapter(adpt);
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        layoutBinding.rcvitems.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext()).paint(paint).build());
        adpt.notifyDataSetChanged();

        layoutBinding.planogram1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentImages fragmentImages = new FragmentImages();
                fragmentImages.shopDto = shopDto;
                fragmentImages.display=displaylist;
                new FragmentUtils(getActivity(), fragmentImages, R.id.fragContainer);

            }
        });

        layoutBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ItemDto itemDto : item) {
                    try {
                        final List<QuantityDto> itemquantity = quantityrepository.queryforitemVisits(new GetAllData(), date, "" + shopDto.getId(), "" + itemDto.getId(), itemDto.getDisplay(), shopDto.getVisitId() + "");
                        // Toast.makeText(getContext(), ""+itemDto.getTitle()+"\n"+itemDto.getQuantity(), Toast.LENGTH_SHORT).show();

                        QuantityDto dto = new QuantityDto();
                        dto.quantity = itemDto.getQuantity();
                        dto.facing=itemDto.getFacing();
                        dto.shopid = "" + shopDto.getId();
                        dto.itemid = "" + itemDto.getId();
                        dto.date = date;
                        dto.visitid = shopDto.getVisitId() + "";
                        dto.display = itemDto.getDisplay();

                        //  Toast.makeText(getContext(), ""+itemDto.getDisplay(), Toast.LENGTH_SHORT).show();
                        if (itemquantity.size() >= 1) {
                            dto.id = itemquantity.get(0).id;
                            quantityrepository.update(dto);
                            //  Toast.makeText(getContext(), "Quantity Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            quantityrepository.add(dto);
                            //  Toast.makeText(getContext(), "Quantity Added", Toast.LENGTH_SHORT).show();
                        }

                    }catch(Exception e){

                    }
                }

                Toast.makeText(getContext(), "Quantity saved", Toast.LENGTH_SHORT).show();
              //  Toast.makeText(getContext(), ""+display, Toast.LENGTH_SHORT).show();
              //  getActivity().getSupportFragmentManager().popBackStack();
                FragmentCheckingAfter fragmentafter=new FragmentCheckingAfter();
                fragmentafter.shopDto = shopDto;
                fragmentafter.display=display;

                new FragmentUtils(getActivity(), fragmentafter, R.id.fragContainer);
            }
        });
        return layoutBinding.getRoot();
    }

    @Override
    public void onTextChanged1(int type, int position, String charSeq) {
        switch (type) {
            case 1:
                item.get(position).setQuantity(charSeq);
                break;
            case 2:
                item.get(position).setFacing(charSeq);
                break;

        }

    }
}
