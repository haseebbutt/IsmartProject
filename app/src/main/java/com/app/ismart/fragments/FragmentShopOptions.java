package com.app.ismart.fragments;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
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
import android.widget.Button;
import android.widget.Toast;

import com.app.ismart.R;
import com.app.ismart.adopters.ShopOptionAdopter;
import com.app.ismart.databinding.FragmentshopoptionsBinding;
import com.app.ismart.dto.CheckedComponentsDto;
import com.app.ismart.dto.CompetitorQuantityDto;
import com.app.ismart.dto.ExpiredItemDto;
import com.app.ismart.dto.FeedbackSubmitDto;
import com.app.ismart.dto.PopSubmitDto;
import com.app.ismart.dto.QuantityDto;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.dto.ShopOptionDto;
import com.app.ismart.dto.ShopStatusDto;
import com.app.ismart.dto.VisitsDto;
import com.app.ismart.interfaces.IonUpdateMark;
import com.app.ismart.rcvbase.RecyclerViewUtils;
import com.app.ismart.realm.RealmController;
import com.app.ismart.realm.repository.BackdoorQuantityRepository;
import com.app.ismart.realm.repository.CheckedComponentsRepository;
import com.app.ismart.realm.repository.ComptitorQuantityRepository;
import com.app.ismart.realm.repository.ExpiredItemRepository;
import com.app.ismart.realm.repository.FeedbackSubmitRepository;
import com.app.ismart.realm.repository.PopSubmitRepository;
import com.app.ismart.realm.repository.QuanityRepository;
import com.app.ismart.realm.repository.ShopOptionsRepository;
import com.app.ismart.realm.repository.VisitsRepository;
import com.app.ismart.realm.specfication.GetAllData;
import com.app.ismart.realm.tables.TableVisits;
import com.app.ismart.utils.DatabaseHelper;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Faheem-Abbas on 6/6/2017.
 */

public class FragmentShopOptions extends Fragment implements IonUpdateMark {
    FragmentshopoptionsBinding layoutBinding;
    public ShopDto shopDto;
    public VisitsDto visitDto;
    String date;
    public String location;
    private RealmController realmController;
    ShopOptionsRepository shopStatusRepository;
    QuanityRepository quanityRepository;
    FeedbackSubmitRepository feedbackSubmitRepository;
    ExpiredItemRepository expiredItemRepository;
    ComptitorQuantityRepository comptitorQuantityRepository;
    BackdoorQuantityRepository backdoorQuantityRepository;
    PopSubmitRepository popSubmitRepository;
    CheckedComponentsRepository checkedComponentsRepository;
    VisitsRepository visitsRepository;
    HashMap<String, Integer> componentHashMap = new HashMap<>();
    DatabaseHelper db;
    Cursor res1=null;
    Cursor res2=null;
    public Button completedBtn;
    View view;
    List<VisitsDto> result;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     //    view = inflater.inflate(R.layout.fragmentshopoptions,container, false);
        layoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragmentshopoptions, container, false);

        ((AppCompatActivity) getActivity()).setSupportActionBar(layoutBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(shopDto.getName());
        layoutBinding.toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        realmController = RealmController.with(this);
        shopStatusRepository = new ShopOptionsRepository(realmController.getRealm());
        checkedComponentsRepository = new CheckedComponentsRepository(realmController.getRealm());
        quanityRepository = new QuanityRepository(realmController.getRealm());
        feedbackSubmitRepository = new FeedbackSubmitRepository(realmController.getRealm());
        expiredItemRepository = new ExpiredItemRepository(realmController.getRealm());
        comptitorQuantityRepository = new ComptitorQuantityRepository(realmController.getRealm());
        popSubmitRepository = new PopSubmitRepository(realmController.getRealm());
        backdoorQuantityRepository = new BackdoorQuantityRepository(realmController.getRealm());
        visitsRepository=new VisitsRepository(realmController.getRealm());

        completedBtn = (Button) layoutBinding.getRoot().findViewById(R.id.shopcomplete);
        componentHashMap.put("Merchandising", R.drawable.icon1);
        componentHashMap.put("Feedback", R.drawable.icon2);
        componentHashMap.put("Expiry", R.drawable.icon3);
        componentHashMap.put("Competitor", R.drawable.icon4);
        componentHashMap.put("Pop", R.drawable.popup);
        componentHashMap.put("Stocktake", R.drawable.ic_menu_send);
        date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());

        completedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   Realm realm = Realm.getDefaultInstance();
               // realm.beginTransaction();


                result=visitsRepository.queryforVisitsOne(new GetAllData(), shopDto.getId());

              int a=  result.get(result.size()-1).getVisitid();
                VisitsDto visitdto=new VisitsDto();


               Toast.makeText(getActivity(), "Visitid:" + a, Toast.LENGTH_LONG).show();
                visitdto.setSchedularid(shopDto.getId());
                visitdto.setVisitid(a);

                visitsRepository.update(visitdto);


                Toast.makeText(getActivity(), "updated !", Toast.LENGTH_LONG).show();
            }
        });

        setAdopter();


        return layoutBinding.getRoot();
    }

    public void setAdopter() {
        List<ShopOptionDto> options = shopStatusRepository.query(new GetAllData());


       db = new DatabaseHelper(getActivity().getApplicationContext());

       res1=db.getCheckedData(shopDto.getId(),1);
    //    Toast.makeText(getActivity(),"shopId:"+shopDto.getId(),Toast.LENGTH_LONG).show();


        if( res1.getCount() == options.size() ) {

            completedBtn.setEnabled(true);


        }else{

            completedBtn.setEnabled(false);
        }

        res1.close();


        if (options.size() >= 1) {
            for (int i = 0; i < options.size(); i++) {
                options.get(i).setIcon(componentHashMap.get(options.get(i).getName()));
                if (options.get(i).getName().equalsIgnoreCase("Merchandising")) {
                    List<QuantityDto> data = quanityRepository.queryfordate(new GetAllData(), date, shopDto.getId() + "");
                    if (data.size() >= 1) {
                        options.get(i).setFilled(true);


                        res1=db.getCheckedDataAll(shopDto.getId(),1);


                           if(res1.getCount()==0) {
                            boolean isInserted = db.insertData(shopDto.getId(), 1, 1);
                            Toast.makeText(getContext(), "Merchandising Data inserted" + isInserted, Toast.LENGTH_LONG).show();
                        }else{

                        //    Toast.makeText(getContext(), "Merchandising Data exists", Toast.LENGTH_LONG).show();
                        }
                    }
                } else if (options.get(i).getName().equalsIgnoreCase("Feedback")) {
                    List<FeedbackSubmitDto> data = feedbackSubmitRepository.queryforfeedbackbydate(new GetAllData(), date, shopDto.getId() + "");
                    if (data.size() >= 1) {
                        options.get(i).setFilled(true);

                         res1=db.getCheckedDataAll(shopDto.getId(),2);

                       if(res1.getCount()==0) {
                            boolean isInserted = db.insertData(shopDto.getId(), 2, 1);

                            Toast.makeText(getContext(), "Feedback Data inserted" + isInserted, Toast.LENGTH_LONG).show();
                        }else{

                         //   Toast.makeText(getContext(), "Feedback Data Exists", Toast.LENGTH_LONG).show();
                        }
                    }
                } else if (options.get(i).getName().equalsIgnoreCase("Expiry")) {
                    List<ExpiredItemDto> data = expiredItemRepository.queryforitem(new GetAllData(), date, shopDto.getId() + "");
                    if (data.size() >= 1) {
                        options.get(i).setFilled(true);
                        res1=db.getCheckedDataAll(shopDto.getId(),3);

                        if(res1.getCount()==0) {
                            boolean isInserted = db.insertData(shopDto.getId(), 3, 1);
                            Toast.makeText(getContext(), "Expiry Data inserted", Toast.LENGTH_LONG).show();
                       }else{

                         //   Toast.makeText(getContext(), "Expiry Data exists", Toast.LENGTH_LONG).show();
                        }
                    }
                } else if (options.get(i).getName().equalsIgnoreCase("Pop")) {
                    List<PopSubmitDto> data = popSubmitRepository.queryfordate(new GetAllData(), date, shopDto.getId() + "");
                    if (data.size() >= 1) {
                        options.get(i).setFilled(true);
                        res1=db.getCheckedDataAll(shopDto.getId(),4);

                           if(res1.getCount()==0) {
                            boolean isInserted = db.insertData(shopDto.getId(), 4, 1);
                            Toast.makeText(getContext(), "POP Data inserted", Toast.LENGTH_LONG).show();
                        }else{

                         //   Toast.makeText(getContext(), "POP data exists", Toast.LENGTH_LONG).show();
                        }
                    }
                } else if (options.get(i).getName().equalsIgnoreCase("Competitor")) {
                    List<CompetitorQuantityDto> data = comptitorQuantityRepository.queryfordate(new GetAllData(), date, shopDto.getId() + "");
                    if (data.size() >= 1) {
                        options.get(i).setFilled(true);
                        res1=db.getCheckedDataAll(shopDto.getId(),5);


                           if(res1.getCount()==0) {
                            boolean isInserted = db.insertData(shopDto.getId(), 5, 1);
                            Toast.makeText(getContext(), "Competitor Data inserted", Toast.LENGTH_LONG).show();
                        }else {

                            //   Toast.makeText(getContext(), "Competitor data exists", Toast.LENGTH_LONG).show();
                           }
                    }
                } else if (options.get(i).getName().equalsIgnoreCase("Stocktake")) {
                    List<QuantityDto> data = backdoorQuantityRepository.queryfordate(new GetAllData(), date, shopDto.getId() + "");
                    if (data.size() >= 1) {
                        options.get(i).setFilled(true);
                        res1=db.getCheckedDataAll(shopDto.getId(),6);

                        if(res1.getCount()==0) {
                            boolean isInserted = db.insertData(shopDto.getId(), 6, 1);
                            Toast.makeText(getContext(), "Stocktake Data inserted", Toast.LENGTH_LONG).show();
                        }else{

                         //   Toast.makeText(getContext(), "Stocktake data exists", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
            res1.close();
            ShopOptionAdopter adapter = new ShopOptionAdopter(options, getContext(), shopDto, location,this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutBinding.rcvoptions.setLayoutManager(RecyclerViewUtils.getGridLayoutManager(getContext(), 1));
            layoutBinding.rcvoptions.setItemAnimator(new DefaultItemAnimator());
            layoutBinding.rcvoptions.setAdapter(adapter);
            Paint paint = new Paint();
            paint.setStrokeWidth(5);
            paint.setColor(Color.parseColor("#E0E3EA"));
            paint.setAntiAlias(true);
            paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
            layoutBinding.rcvoptions.addItemDecoration(
                    new HorizontalDividerItemDecoration.Builder(getContext()).paint(paint).build());

            res1=db.getCheckedData(shopDto.getId(),1);
            if( res1.getCount() == options.size() ) {

                Toast.makeText(getActivity(), "Records :" + res1.getCount(), Toast.LENGTH_LONG).show();
                //  Toast.makeText(getActivity(), "Component id :" + res1.getString(2), Toast.LENGTH_LONG).show();
                // Toast.makeText(getActivity(), "Completed:" + res1.getString(3), Toast.LENGTH_LONG).show();
                completedBtn.setEnabled(true);


            }else{

                Toast.makeText(getActivity(), "Records else" + res1.getCount(), Toast.LENGTH_LONG).show();
                completedBtn.setEnabled(false);
            }

            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateui() {
        setAdopter();
    }
}
