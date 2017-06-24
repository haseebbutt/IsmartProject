package com.app.ismart.adopters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.app.ismart.MainActivity;
import com.app.ismart.R;
import com.app.ismart.databinding.ShopoptionItemBinding;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.dto.ShopOptionDto;
import com.app.ismart.fragments.FragmentBackdoorCategories;
import com.app.ismart.fragments.FragmentCategory;
import com.app.ismart.fragments.FragmentComptitorDisplay;
import com.app.ismart.fragments.FragmentDisplay;
import com.app.ismart.fragments.FragmentFeedbackCategory;
import com.app.ismart.fragments.FragmentPopSubmit;
import com.app.ismart.interfaces.IonUpdateMark;
import com.app.ismart.rcvbase.BaseRecyclerViewAdapter;
import com.app.ismart.rcvbase.IOnItemClickListner;
import com.app.ismart.utils.FragmentUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Faheem-Abbas on 6/6/2017.
 */

public class ShopOptionAdopter extends BaseRecyclerViewAdapter<ShopOptionDto, ShopoptionItemBinding> implements IOnItemClickListner<ShopOptionDto> {
    ShopDto shopDto;
    String location;
    String type="backdoor";
    public  IonUpdateMark ionUpdateMark;
    public ShopOptionAdopter(List<ShopOptionDto> data, @NotNull Context context, ShopDto shopDto, String location, IonUpdateMark ionUpdateMark) {
        super(data, context);
        this.shopDto=shopDto;
        this.location=location;
        this.ionUpdateMark=ionUpdateMark;
    }

    @Override
    public void onRecyclerItemClick(ShopOptionDto model, View view, int position) {
        if(model.getName().equalsIgnoreCase("Merchandising")){
            FragmentDisplay fargment = new FragmentDisplay();
            fargment.shopDto = shopDto;
            fargment.location=location;
            fargment.ionUpdateMark=ionUpdateMark;
            new FragmentUtils((Activity) context, fargment, R.id.fragContainer);
        }
        else  if(model.getName().equalsIgnoreCase("Feedback")){
            FragmentFeedbackCategory fargment = new FragmentFeedbackCategory();
            fargment.shopDto = shopDto;
            fargment.ionUpdateMark=ionUpdateMark;
            new FragmentUtils((Activity) context, fargment, R.id.fragContainer);
        }
        else  if(model.getName().equalsIgnoreCase("Expiry")){
            FragmentCategory fragmentCategory= new FragmentCategory();
            fragmentCategory.shopDto = shopDto;
            fragmentCategory.ionUpdateMark=ionUpdateMark;
            new FragmentUtils((Activity) context, fragmentCategory, R.id.fragContainer);
        }
        else  if(model.getName().equalsIgnoreCase("Pop")){
            FragmentPopSubmit fargment = new FragmentPopSubmit();
            fargment.shopDto = shopDto;
            fargment.ionUpdateMark=ionUpdateMark;
            new FragmentUtils((Activity) context, fargment, R.id.fragContainer);
        }
        else  if(model.getName().equalsIgnoreCase("Competitor")){
            FragmentComptitorDisplay fargment = new FragmentComptitorDisplay();
            fargment.shopDto = shopDto;
            fargment.ionUpdateMark=ionUpdateMark;
            new FragmentUtils((Activity) context, fargment, R.id.fragContainer);
        }
        else  if(model.getName().equalsIgnoreCase("Stocktake")){
         shoptype(shopDto);
        }

    }

    @Override
    protected View onCreateViewHolderDynamic(Context context, LayoutInflater inflater, ViewGroup viewGroup, int viewType) {
        return inflater.inflate(R.layout.shopoption_item, null);
    }

    @Override
    protected void onBindViewHolderDynamic(ShopOptionDto item, ViewHolder viewHolder, int position) {
        viewHolder.binding.txttitle.setText(item.getName());
        if(item.getIcon()!=0) {
            viewHolder.binding.imgicon.setImageResource(item.getIcon());
        }
        if(item.isFilled()){
            viewHolder.binding.imgtick.setVisibility(View.VISIBLE);
        }
        else{
            viewHolder.binding.imgtick.setVisibility(View.INVISIBLE);
        }
        setOnItemClickListner(this);
    }

    @Override
    protected void onViewHolderCreation(View itemView) {

    }

    @Override
    protected void onViewHolderCreated(ViewHolder viewHolder) {

    }
    public void shoptype(final ShopDto shopDto) {


        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.shopbackdoordailog, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final RadioGroup userInput = (RadioGroup) promptsView
                .findViewById(R.id.myRadioGroup);

        Button submit = (Button) promptsView
                .findViewById(R.id.btnSubmit);
        final Button cancel = (Button) promptsView
                .findViewById(R.id.btnCancel);



        // create alert progressdialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String location= ((MainActivity) context).checklocation();
                if(location!=null) {
                    int checkedRadioButtonId = userInput.getCheckedRadioButtonId();
                    if (checkedRadioButtonId == -1) {
                        // No item selected
                    } else {
                        if (checkedRadioButtonId == R.id.front) {
                            type="front";
                        } else if (checkedRadioButtonId == R.id.back) {
                            type="backdoor";
                        }

                    }
                    if (type.equalsIgnoreCase("backdoor")) {
                        FragmentBackdoorCategories fargment = new FragmentBackdoorCategories();
                        fargment.shopDto = shopDto;
                        fargment.location=location;
                        fargment.ionUpdateMark=ionUpdateMark;
                        new FragmentUtils((Activity) context, fargment, R.id.fragContainer);
                        ((MainActivity) context).enableshoplist(false);
                    }

                }
                alertDialog.cancel();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });


    }
}
