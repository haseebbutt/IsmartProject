package com.app.ismart.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.app.ismart.MainActivity;
import com.app.ismart.R;
import com.app.ismart.adopters.DisplayAdopter;
import com.app.ismart.api.IApiCalls;
import com.app.ismart.databinding.FragmentCategoryBinding;
import com.app.ismart.dto.CategoryDto;
import com.app.ismart.dto.DisplayDto;
import com.app.ismart.dto.ItemDto;
import com.app.ismart.dto.Products;
import com.app.ismart.dto.QuantityDto;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.interfaces.IonUpdateMark;
import com.app.ismart.realm.RealmController;
import com.app.ismart.realm.repository.ProductRepository;
import com.app.ismart.realm.repository.QuanityRepository;
import com.app.ismart.realm.specfication.GetAllData;
import com.app.ismart.rest.APIError;
import com.app.ismart.rest.IRestResponseListner;
import com.app.ismart.restmanagers.LatestProductManager;
import com.app.ismart.retrofit.RetrofitClient;
import com.app.ismart.utils.FragmentUtils;
import com.app.ismart.utils.InternetConnection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * Created by HP 2 on 4/22/2017.
 */

public class FragmentDisplay extends Fragment implements IRestResponseListner<List<Products>> {
    public ShopDto shopDto;
    static FragmentCategoryBinding layoutBinding;
    private DisplayAdopter expandableListAdapter;
    HashMap<String, List<CategoryDto>> expandableListDetail = new HashMap<String, List<CategoryDto>>();
    List<ItemDto> expandableListItemsDetail = new ArrayList<>();
    List<String> expandableListTitle;
    ArrayList<DisplayDto> displaylist = new ArrayList<>();
    ArrayList<String> displays = new ArrayList<>();
    ProgressDialog progressdialog;
    private RealmController realmController;
    ProductRepository repository;
    QuanityRepository quantityrepository;
    String date;
    public String location;
    String type = "backdoor";
    ArrayList<String> itemid=new ArrayList<>();
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
        layoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(layoutBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(shopDto.getName());
        layoutBinding.toolbar.setTitleTextColor(Color.WHITE);
        progressdialog = new ProgressDialog(getContext());
        progressdialog.setMessage("Loading please wait....");
        progressdialog.show();
        date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        realmController = RealmController.with(this);
        repository = new ProductRepository(realmController.getRealm());
        quantityrepository = new QuanityRepository(realmController.getRealm());

        View view=layoutBinding.getRoot();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        List<ItemDto> categories = repository.queryforshop(new GetAllData(), shopDto.getId() + "");
        if (categories.size() >= 1) {
            setAdopter(categories);
            progressdialog.dismiss();
        } else {
            if (InternetConnection.checkConnection(getContext())) {
                IApiCalls api = RetrofitClient.instance.retrofit.create(IApiCalls.class);
                Call<List<Products>> apiCall = api.getProductsLatest(shopDto.getId() + "");
                apiCall.enqueue(new LatestProductManager(this));
            } else {
                onErrorResponse(null);


            }
        }
     /*   layoutBinding.imgtakephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentChecking fragmentChecking = new FragmentChecking();
                fragmentChecking.shopDto = shopDto;
                new FragmentUtils(getActivity(), fragmentChecking, R.id.fragContainer);
            }
        });
        layoutBinding.imgdisplayimages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentImages fragmentImages = new FragmentImages();
                fragmentImages.shopDto = shopDto;
                fragmentImages.display=displaylist;
                new FragmentUtils(getActivity(), fragmentImages, R.id.fragContainer);
            }
        }); */
        layoutBinding.expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                itemid.clear();
                String display = expandableListTitle.get(groupPosition);

              //  Toast.makeText(getActivity(),""+display,Toast.LENGTH_LONG).show();
                CategoryDto item = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);

            //    Toast.makeText(getActivity(),"item:"+item.getName(),Toast.LENGTH_LONG).show();
                FragmentTakeQuantity fragment = new FragmentTakeQuantity();
                fragment.item = filterList(display, item.getName(), expandableListItemsDetail);
                fragment.shopDto = shopDto;
                new FragmentUtils(getActivity(), fragment, R.id.fragContainer);

                return false;
            }
        });


        return view;
    }

    public void setAdopter(List<ItemDto> list) {
        expandableListDetail.clear();
        expandableListItemsDetail.clear();
        displays.clear();
        expandableListItemsDetail = list;
        for (ItemDto itemDto : list) {
            if (!displays.contains(itemDto.getDisplay())) {
                displays.add(itemDto.getDisplay());
                DisplayDto dto=new DisplayDto();
                dto.display=itemDto.getDisplay();
                dto.displayimage=itemDto.getImageurl();
                dto.displayid=itemDto.getDisplayid();
                displaylist.add(dto);
            }
        }
        for (String display : displays) {
            expandableListDetail.put(display, filterListBycategory(display, list));
        }

        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new DisplayAdopter(getContext(), expandableListTitle, expandableListDetail,displaylist,shopDto);
        layoutBinding.expandableListView.setAdapter(expandableListAdapter);
    }

    public List<ItemDto> filterList(String display, String category, List<ItemDto> list) {
        List<ItemDto> items = new ArrayList<>();
        for (ItemDto itemDto : list) {
            if (itemDto.getCategory().equalsIgnoreCase(category) && itemDto.getDisplay().equalsIgnoreCase(display) && !itemid.contains(itemDto.getId()+"")) {
                items.add(itemDto);
                itemid.add(itemDto.getId()+"");
                final List<QuantityDto> itemquantity = quantityrepository.queryforitemVisits(new GetAllData(), date, "" + shopDto.getId(), "" + itemDto.getId(),display,""+shopDto.getVisitId());
                if (itemquantity.size() >= 1) {
                    itemDto.setQuantity(itemquantity.get(0).quantity);
                }

            }
        }
        return items;
    }

    public List<CategoryDto> filterListBycategory(String category, List<ItemDto> list) {
        List<CategoryDto> items = new ArrayList<>();
        List<String> subitems = new ArrayList<>();
        for (ItemDto itemDto : list) {
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

    @Override
    public void onSuccessResponse(List<Products> model) {
        progressdialog.dismiss();
        repository.removeAll();
        ArrayList<ItemDto> items = new ArrayList<>();
        for (Products products : model) {
            ItemDto item = new ItemDto();
            item.setDisplay(products.getDisplay());
            item.setCategory(products.getCategory());
            item.setTitle(products.getTitle());
            item.setId(products.getId());
            item.setShopid(shopDto.getId() + "");
            item.setDisplayid(products.getDisplayId()+"");
            items.add(item);

        }
        repository.add(items);
        setAdopter(items);
        ;
    }

    @Override
    public void onErrorResponse(APIError erroModel) {
        progressdialog.dismiss();
        List<ItemDto> categories = repository.query(new GetAllData());
        setAdopter(categories);
        if (erroModel != null) {
            Log.d("Api call failure", erroModel.message);
        }
        //setAdopter();
    }


    public static void enableClick(boolean enable) {
        layoutBinding.expandableListView.setEnabled(enable);
    }

}
