package com.app.ismart.adopters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.app.ismart.R;
import com.app.ismart.databinding.FeedbackItemBinding;
import com.app.ismart.dto.FeedBackAnswersDto;
import com.app.ismart.dto.FeedBackDto;
import com.app.ismart.interfaces.OnEditTextChanged;
import com.app.ismart.rcvbase.BaseRecyclerViewAdapter;
import com.app.ismart.realm.RealmController;
import com.app.ismart.realm.repository.FeedbackAnswersRepository;
import com.app.ismart.realm.specfication.GetAllData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faheem-Abbas on 5/25/2017.
 */

public class FeedbackAdopter extends BaseRecyclerViewAdapter<FeedBackDto, FeedbackItemBinding> {
    private OnEditTextChanged onEditTextChanged;

    List<FeedBackAnswersDto> answersList;
    FeedbackAnswersRepository repositoryFeedbackAnswers;
    RealmController realmController;
    RadioButton button;

    public FeedbackAdopter(List<FeedBackDto> data, @NotNull Context context, OnEditTextChanged onEditTextChanged) {
        super(data, context);
        this.onEditTextChanged = onEditTextChanged;
    }

    @Override
    protected View onCreateViewHolderDynamic(Context context, LayoutInflater inflater, ViewGroup viewGroup, int viewType) {
        return inflater.inflate(R.layout.feedback_item, null);
    }

    @Override
    protected void onBindViewHolderDynamic(FeedBackDto item, ViewHolder viewHolder, final int position) {
        viewHolder.binding.txtname.setText(item.getFeedback());

        realmController = RealmController.with((Activity)context);
        repositoryFeedbackAnswers = new FeedbackAnswersRepository(realmController.getRealm());

        final List<FeedBackAnswersDto> feed=  repositoryFeedbackAnswers.queryforfeedbackAnswers(new GetAllData(),item.getFeedbackid());
      //  Toast.makeText((Activity)context, "Answers:" + feed.get(0).getFeedbackid(), Toast.LENGTH_LONG).show();


        for(int k=0; k<feed.size() ; k++){

            button = new RadioButton(context);
            button.setText("" +feed.get(k).getAnswers());
          //  button.setTag(k+position);

            viewHolder.binding.myRadioGroup.addView(button);


        }


     /*   if (item.getAnswers().equals("Yes")) {
            ((RadioButton) viewHolder.binding.myRadioGroup.findViewById(R.id.rdoyes)).setChecked(true);
        } else if (item.getAnswers().equals("No")) {
            ((RadioButton) viewHolder.binding.myRadioGroup.findViewById(R.id.rdono)).setChecked(true);
        } */

        viewHolder.binding.myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                for(int i=0; i<group.getChildCount(); i++) {
                    RadioButton btn = (RadioButton) group.getChildAt(i);
                    if (btn.getId() == checkedId) {
                        CharSequence text = btn.getText();

                        onEditTextChanged.onTextChanged(position, "" + text);
                     //  Toast.makeText(context, "id:" + btn.getId(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }

            //  String a= button.getTag().toString();
           //     Toast.makeText(context,"tag:"+a,Toast.LENGTH_LONG).show();
             //   Toast.makeText(context,"check:"+checkedId,Toast.LENGTH_LONG).show();


            //   if (checkedId== button.getTag()) {
              //      onEditTextChanged.onTextChanged(position, ""+feed.get(checkedId).getAnswerid());

             //   }

            }
        });


    }

    @Override
    protected void onViewHolderCreation(View itemView) {

    }

    @Override
    protected void onViewHolderCreated(ViewHolder viewHolder) {

    }
}
