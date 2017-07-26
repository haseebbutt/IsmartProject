package com.app.ismart.adopters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.ismart.R;
import com.app.ismart.databinding.TakequantityItemBinding;
import com.app.ismart.dto.ItemDto;
import com.app.ismart.interfaces.OnEditTextChanged;
import com.app.ismart.rcvbase.BaseRecyclerViewAdapter;
import com.app.ismart.realm.repository.QuanityRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Faheem-Abbas on 5/22/2017.
 */

public class TakeQuantityAdopter extends BaseRecyclerViewAdapter<ItemDto, TakequantityItemBinding> {
    private OnEditTextChanged onEditTextChanged;

    public TakeQuantityAdopter(List<ItemDto> data, @NotNull Context context, OnEditTextChanged onEditTextChanged) {
        super(data, context);
        this.onEditTextChanged=onEditTextChanged;
    }

    @Override
    protected View onCreateViewHolderDynamic(Context context, LayoutInflater inflater, ViewGroup viewGroup, int viewType) {
        return inflater.inflate(R.layout.takequantity_item, null);
    }

    @Override
    protected void onBindViewHolderDynamic(ItemDto item, ViewHolder viewHolder, final int position) {

        viewHolder.binding.txtname.setText(item.getTitle());
        viewHolder.binding.edtquantity.setText(item.getQuantity());




        viewHolder.binding.edtquantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onEditTextChanged.onTextChanged(position, charSequence.toString());
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
