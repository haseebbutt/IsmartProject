package com.app.ismart.adopters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.ismart.R;
import com.app.ismart.databinding.ExpiiredItemBinding;
import com.app.ismart.dto.ExpiredItemDto;
import com.app.ismart.interfaces.IOnExpired;
import com.app.ismart.rcvbase.BaseRecyclerViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Faheem-Abbas on 6/3/2017.
 */

public class ExpiredItemsAdopter extends BaseRecyclerViewAdapter<ExpiredItemDto, ExpiiredItemBinding> {
    private IOnExpired onEditTextChanged;
    public ExpiredItemsAdopter(List<ExpiredItemDto> data, @NotNull Context context, IOnExpired onEditTextChanged) {
        super(data, context);
        this.onEditTextChanged=onEditTextChanged;
    }

    @Override
    protected View onCreateViewHolderDynamic(Context context, LayoutInflater inflater, ViewGroup viewGroup, int viewType) {
        return inflater.inflate(R.layout.expiired_item, null);
    }

    @Override
    protected void onBindViewHolderDynamic(ExpiredItemDto item, ViewHolder viewHolder, final int position) {
        viewHolder.binding.txtname.setText(item.itemname);
        viewHolder.binding.edtexpired.setText(item.expired);
        viewHolder.binding.edtexpired.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onEditTextChanged.onTextChanged(1,position, charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        viewHolder.binding.edtnearexpired.setText(item.nearexpired);
        viewHolder.binding.edtnearexpired.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onEditTextChanged.onTextChanged(2,position, charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    @Override
    protected void onViewHolderCreation(View itemView) {

    }

    @Override
    protected void onViewHolderCreated(ViewHolder viewHolder) {

    }
}
