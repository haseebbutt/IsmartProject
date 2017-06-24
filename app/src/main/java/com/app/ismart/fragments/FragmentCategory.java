package com.app.ismart.fragments;

import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.ismart.MainActivity;
import com.app.ismart.R;
import com.app.ismart.adopters.CategoryAdopter;
import com.app.ismart.api.IApiCalls;
import com.app.ismart.databinding.FragmentcategorylistBinding;
import com.app.ismart.dto.CategoryDto;
import com.app.ismart.dto.ItemDto;
import com.app.ismart.dto.QuantityDto;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.interfaces.IonUpdateMark;
import com.app.ismart.rcvbase.RecyclerViewUtils;
import com.app.ismart.realm.RealmController;
import com.app.ismart.realm.repository.ProductRepository;
import com.app.ismart.realm.repository.QuanityRepository;
import com.app.ismart.realm.specfication.GetAllData;
import com.app.ismart.rest.APIError;
import com.app.ismart.rest.IRestResponseListner;
import com.app.ismart.restmanagers.ProductsManager;
import com.app.ismart.retrofit.RetrofitClient;
import com.app.ismart.utils.InternetConnection;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Faheem-Abbas on 5/22/2017.
 */

public class FragmentCategory extends Fragment implements IRestResponseListner<List<ItemDto>> {
    public ShopDto shopDto;
    static FragmentcategorylistBinding layoutBinding;
    HashMap<String, List<ItemDto>> expandableListDetail = new HashMap<String, List<ItemDto>>();
    List<CategoryDto> expandableListTitle = new ArrayList<>();
    ArrayList<String> categories = new ArrayList<>();
    ProgressDialog progressdialog;
    private RealmController realmController;
    ProductRepository repository;
    QuanityRepository quantityrepository;

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
        layoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragmentcategorylist, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(layoutBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(shopDto.getName());
        layoutBinding.toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressdialog = new ProgressDialog(getContext());

        date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        realmController = RealmController.with(this);
        repository = new ProductRepository(realmController.getRealm());
        quantityrepository = new QuanityRepository(realmController.getRealm());


        List<ItemDto> categories = repository.queryforshop(new GetAllData(), shopDto.getId() + "");
        if (categories.size() >= 1) {
            setAdopter(categories);
        } else {
            progressdialog.setMessage("Loading please wait....");
            progressdialog.show();
            if (InternetConnection.checkConnection(getContext())) {

                IApiCalls api = RetrofitClient.instance.retrofit.create(IApiCalls.class);
                Call<List<ItemDto>> apiCall = api.getProducts();
                apiCall.enqueue(new ProductsManager(this));
            } else {
                onErrorResponse(null);


            }
        }

        return layoutBinding.getRoot();
    }

    public void setAdopter(List<ItemDto> list) {
        expandableListDetail.clear();
        categories.clear();
        ;
        for (ItemDto itemDto : list) {
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

        CategoryAdopter adapter = new CategoryAdopter(expandableListTitle, getContext(), expandableListDetail, shopDto);
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

    public List<ItemDto> filterList(String category, List<ItemDto> list) {
        List<ItemDto> items = new ArrayList<>();
        for (ItemDto itemDto : list) {
            if (itemDto.getCategory().equalsIgnoreCase(category)) {
                final List<QuantityDto> itemquantity = quantityrepository.queryforitem(new GetAllData(), date, "" + shopDto.getId(), "" + itemDto.getId());
                if (itemquantity.size() >= 1) {
                    itemDto.setQuantity(itemquantity.get(0).quantity);
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

    @Override
    public void onSuccessResponse(List<ItemDto> model) {
        progressdialog.dismiss();
        repository.removeAll();
        repository.add(model);
        setAdopter(model);
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
        layoutBinding.rcvcategory.setEnabled(enable);
    }
}