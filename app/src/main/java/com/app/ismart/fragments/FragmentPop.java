package com.app.ismart.fragments;

import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.ismart.R;
import com.app.ismart.adopters.PopListAdopter;
import com.app.ismart.api.IApiCalls;
import com.app.ismart.databinding.FrgmentpopBinding;
import com.app.ismart.dto.Pop;
import com.app.ismart.rcvbase.RecyclerViewUtils;
import com.app.ismart.realm.RealmController;
import com.app.ismart.realm.repository.PopRepository;
import com.app.ismart.realm.specfication.GetAllData;
import com.app.ismart.rest.APIError;
import com.app.ismart.rest.IRestResponseListner;
import com.app.ismart.restmanagers.PopManger;
import com.app.ismart.retrofit.RetrofitClient;
import com.app.ismart.utils.InternetConnection;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Faheem-Abbas on 5/21/2017.
 */

public class FragmentPop extends Fragment implements IRestResponseListner<List<Pop>> {
    FrgmentpopBinding layoutBinding;
    ProgressDialog progressdialog;
    private RealmController realmController;
    PopRepository repository;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBinding = DataBindingUtil.inflate(inflater, R.layout.frgmentpop, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(layoutBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Pops");
        layoutBinding.toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressdialog = new ProgressDialog(getContext());
        progressdialog.setMessage("Loading please wait....");
        progressdialog.show();
        realmController = RealmController.with(this);
        repository = new PopRepository(realmController.getRealm());
        List<Pop> pops = repository.query(new GetAllData());
        if(pops.size()>=1) {
            setDataAdopter(pops);
            progressdialog.dismiss();
        }
        else {
            if (InternetConnection.checkConnection(getContext())) {
                IApiCalls api = RetrofitClient.instance.retrofit.create(IApiCalls.class);
                Call<List<Pop>> apiCall = api.getpops();
                apiCall.enqueue(new PopManger(this));
            } else {


                Toast.makeText(getContext(), "No internet available", Toast.LENGTH_SHORT).show();


            }
        }
        return layoutBinding.getRoot();
    }

    @Override
    public void onSuccessResponse(List<Pop> model) {
        repository.removeAll();
        repository.add(model);
        progressdialog.dismiss();
        setDataAdopter(model);
    }

    @Override
    public void onErrorResponse(APIError erroModel) {
        progressdialog.dismiss();


        if (erroModel != null) {
            Log.d("Api call failure", erroModel.message);
        }
        List<Pop> pops = repository.query(new GetAllData());
        setDataAdopter(pops);
    }

    private void setDataAdopter(List<Pop> data) {


        PopListAdopter adapter = new PopListAdopter(data, getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutBinding.rcvpops.setLayoutManager(RecyclerViewUtils.getGridLayoutManager(getContext(), 1));
        layoutBinding.rcvpops.setItemAnimator(new DefaultItemAnimator());
        layoutBinding.rcvpops.setAdapter(adapter);
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        layoutBinding.rcvpops.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext()).paint(paint).build());
        adapter.notifyDataSetChanged();

    }

}
