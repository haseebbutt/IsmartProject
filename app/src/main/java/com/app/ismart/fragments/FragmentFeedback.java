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

import com.app.ismart.MainActivity;
import com.app.ismart.R;
import com.app.ismart.adopters.FeedbackAdopter;
import com.app.ismart.databinding.FragmentFeedbackBinding;
import com.app.ismart.dto.FeedBackAnswersDto;
import com.app.ismart.dto.FeedBackDto;
import com.app.ismart.dto.FeedbackSubmitDto;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.interfaces.OnEditTextChanged;
import com.app.ismart.rcvbase.RecyclerViewUtils;
import com.app.ismart.realm.RealmController;
import com.app.ismart.realm.repository.FeedbackAnswersRepository;
import com.app.ismart.realm.repository.FeedbackRepository;
import com.app.ismart.realm.repository.FeedbackSubmitRepository;
import com.app.ismart.realm.specfication.GetAllData;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Faheem-Abbas on 5/25/2017.
 */

public class FragmentFeedback extends Fragment implements OnEditTextChanged {
    FragmentFeedbackBinding layoutBinding;

    private RealmController realmController;
    FeedbackRepository repositoryFeedback;
    FeedbackSubmitRepository repositoryFeedbackSubmit;
    public List<FeedBackDto> data = new ArrayList<>();
    List<FeedBackAnswersDto> answersList;
    FeedbackAnswersRepository repositoryFeedbackAnswers;
    public ShopDto shopDto;
String date;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedback, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(layoutBinding.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Pops");
        layoutBinding.toolbar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        realmController = RealmController.with(this);
        repositoryFeedback = new FeedbackRepository(realmController.getRealm());
        repositoryFeedbackSubmit = new FeedbackSubmitRepository(realmController.getRealm());


        if (data.size() >= 1) {

            setdataAdopter();


        }

        layoutBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = ((MainActivity) getActivity()).checklocation();
                if (location != null) {
                    for (FeedBackDto itemDto : data) {
                        FeedbackSubmitDto dto = new FeedbackSubmitDto();
                        dto.location = location;
                        dto.shopId = shopDto.getId() + "";
                        dto.feedbackId = itemDto.getFeedbackid();
                        dto.response = itemDto.getAnswers();
                        dto.date=date;
                        dto.visitId=""+shopDto.getVisitId();
                        List<FeedbackSubmitDto> exists = repositoryFeedbackSubmit.queryforfeedback(new GetAllData(), shopDto.getId() + "", itemDto.getFeedbackid(),""+shopDto.getVisitId());
                        if (exists.size() >= 1) {
                            repositoryFeedbackSubmit.update(dto);
                        } else {
                            repositoryFeedbackSubmit.add(dto);
                        }

                    }
                    Toast.makeText(getContext(), "Feedback saved", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
        return layoutBinding.getRoot();
    }


    private void setdataAdopter() {
        for (int i = 0; i < data.size(); i++) {

            List<FeedbackSubmitDto> exists = repositoryFeedbackSubmit.queryforfeedback(new GetAllData(), shopDto.getId() + "", data.get(i).getId() + "",shopDto.getVisitId()+"");
            if (exists.size() >= 1) {
                data.get(i).setAnswers(exists.get(0).response);
            }
        }

        FeedbackAdopter adapter = new FeedbackAdopter(data, getContext(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutBinding.rcvfeedback.setLayoutManager(RecyclerViewUtils.getGridLayoutManager(getContext(), 1));
        layoutBinding.rcvfeedback.setItemAnimator(new DefaultItemAnimator());
        layoutBinding.rcvfeedback.setAdapter(adapter);
        Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        layoutBinding.rcvfeedback.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext()).paint(paint).build());
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onTextChanged(int position, String charSeq) {
        data.get(position).setAnswers(charSeq);
    }
}