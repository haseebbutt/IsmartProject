package com.app.ismart.adopters;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ismart.MainActivity;
import com.app.ismart.R;
import com.app.ismart.databinding.ShopItemsBinding;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.dto.VisitsDto;
import com.app.ismart.fragments.FragmentShopClose;
import com.app.ismart.rcvbase.BaseRecyclerViewAdapter;
import com.app.ismart.rcvbase.IOnItemClickListner;
import com.app.ismart.realm.RealmController;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.repository.ShopsRepository;
import com.app.ismart.realm.repository.VisitsRepository;
import com.app.ismart.realm.specfication.GetAllData;
import com.app.ismart.realm.tables.TableShops;
import com.app.ismart.realm.tables.TableVisits;
import com.app.ismart.utils.ArrayList1;
import com.app.ismart.utils.FragmentUtils;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

/**
 * Created by HP 2 on 4/22/2017.
 */

public class ShopsListAdopter extends BaseRecyclerViewAdapter<ShopDto, ShopItemsBinding> implements IOnItemClickListner<ShopDto> {
    public Context context;
    String status = "open";
    VisitsRepository visitsRepository;
    ShopsRepository shopsRepository;
    List<VisitsDto> result;
    List<ShopDto> resultShops;
    List<VisitsDto> high;
    RealmController realmController;
    Realm realm;
    ImageView imageview;
    TextView textView;
    private ArrayList<Integer> visitedIDList = null;
    View view=null;

    public ShopsListAdopter(List<ShopDto> data, @NotNull Context context) {
        super(data, context);
        this.context = context;


    }

    @Override
    protected View onCreateViewHolderDynamic(Context context, LayoutInflater inflater, ViewGroup viewGroup, int viewType) {


        view=inflater.inflate(R.layout.shop_items, null);
       // imageview=(ImageView)view.findViewById(R.id.imageView2);
    //    textView=(TextView)view.findViewById(R.id.txtShopName);

        return view;


    }

    @Override
    protected void onBindViewHolderDynamic(ShopDto item, ViewHolder viewHolder, int position) {

       realmController = RealmController.with((Activity) context);
        visitsRepository=new VisitsRepository(realmController.getRealm());
        result=visitsRepository.queryforVisitsTwo(new GetAllData(), item.getId());

            if(result.size()!=0){


                viewHolder.binding.imageView3.setVisibility(View.VISIBLE);
            //    Toast.makeText(context,"Present;"+result.get(0).getSchedularid(),Toast.LENGTH_LONG).show();


          }else{
                viewHolder.binding.imageView3.setVisibility(View.INVISIBLE);

            }

        viewHolder.binding.setShopDto(item);
        setOnItemClickListner(this);
    }

    @Override
    protected void onViewHolderCreation(View itemView) {

    }

    @Override
    protected void onViewHolderCreated(ViewHolder viewHolder) {

    }


    @Override
    public void onRecyclerItemClick(ShopDto model, View view, int position) {



        realmController = RealmController.with((Activity) context);
        visitsRepository=new VisitsRepository(realmController.getRealm());
       shopsRepository=new ShopsRepository(realmController.getRealm());
        realm = Realm.getDefaultInstance();



         high= visitsRepository.queryforVisitsOne(new GetAllData(),model.getId());


        int visit=high.get(0).getVisitid();
        model.setVisitId(visit);

        shopStatus(model);

     int Schedularid= model.getId();

    }

    public void shopStatus(final ShopDto shopDto) {


        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.shopstatus_dailog, null);

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

                String location = ((MainActivity) context).checklocation();

                if (location != null) {
                    int checkedRadioButtonId = userInput.getCheckedRadioButtonId();
                    if (checkedRadioButtonId == -1) {
                        // No item selected
                    } else {
                        if (checkedRadioButtonId == R.id.open) {
                            status = "open";
                        } else if (checkedRadioButtonId == R.id.close) {
                            status = "close";
                        }

                    }
                    boolean shopOpen = true;
                    if (status.equalsIgnoreCase("open")) {
                        shopOpen = true;
                    } else {
                        shopOpen = false;
                    }
                    FragmentShopClose fargment = new FragmentShopClose();
                    fargment.shopDto = shopDto;
                    fargment.location = location;
                    fargment.isShopenOpen = shopOpen;
                    new FragmentUtils((Activity) context, fargment, R.id.fragContainer);

                    ((MainActivity) context).enableshoplist(false);
                    ((MainActivity) context).onResume();
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
