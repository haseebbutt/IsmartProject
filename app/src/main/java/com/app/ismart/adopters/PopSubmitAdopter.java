package com.app.ismart.adopters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.ismart.R;
import com.app.ismart.databinding.PopsubmitItemBinding;
import com.app.ismart.dto.Pop;
import com.app.ismart.interfaces.IonTakePhoto;
import com.app.ismart.interfaces.OnEditTextChanged;
import com.app.ismart.rcvbase.BaseRecyclerViewAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Faheem-Abbas on 5/29/2017.
 */

public class PopSubmitAdopter extends BaseRecyclerViewAdapter<Pop, PopsubmitItemBinding> {
    private OnEditTextChanged onEditTextChanged;
    private IonTakePhoto ionTakePhoto;

    public PopSubmitAdopter(List<Pop> data, @NotNull Context context, OnEditTextChanged onEditTextChanged, IonTakePhoto ionTakePhoto) {
        super(data, context);
        this.onEditTextChanged = onEditTextChanged;
        this.ionTakePhoto = ionTakePhoto;
    }

    @Override
    protected View onCreateViewHolderDynamic(Context context, LayoutInflater inflater, ViewGroup viewGroup, int viewType) {
        return inflater.inflate(R.layout.popsubmit_item, null);
    }

    @Override
    protected void onBindViewHolderDynamic(Pop item, ViewHolder viewHolder, final int position) {
        viewHolder.binding.txtpopname.setText(item.getName());
        viewHolder.binding.edtquantity.setText(item.getQuantity());
        viewHolder.binding.edtquantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onEditTextChanged.onTextChanged(position, charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        viewHolder.binding.imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ionTakePhoto.onTakePhoto(position);
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
