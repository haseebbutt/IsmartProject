package com.app.ismart.adopters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.ismart.R;
import com.app.ismart.databinding.FeedbackItemBinding;
import com.app.ismart.dto.FeedBackDto;
import com.app.ismart.interfaces.OnEditTextChanged;
import com.app.ismart.rcvbase.BaseRecyclerViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Faheem-Abbas on 5/25/2017.
 */

public class FeedbackAdopter extends BaseRecyclerViewAdapter<FeedBackDto, FeedbackItemBinding> {
    private OnEditTextChanged onEditTextChanged;

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
        if (item.getAnswers().equals("Yes")) {
            ((RadioButton) viewHolder.binding.myRadioGroup.findViewById(R.id.rdoyes)).setChecked(true);
        } else if (item.getAnswers().equals("No")) {
            ((RadioButton) viewHolder.binding.myRadioGroup.findViewById(R.id.rdono)).setChecked(true);
        }
        viewHolder.binding.myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rdoyes) {
                    onEditTextChanged.onTextChanged(position, "Yes");

                } else if (checkedId == R.id.rdono) {
                    onEditTextChanged.onTextChanged(position, "No");
                }

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
