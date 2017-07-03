package com.app.ismart;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ismart.adopters.ShopsListAdopter;
import com.app.ismart.api.IApiCalls;
import com.app.ismart.async.AsyncDispatcher;
import com.app.ismart.async.IAsync;
import com.app.ismart.databinding.ActivityMainBinding;
import com.app.ismart.dto.CompetitorProductsDto;
import com.app.ismart.dto.CompetitorQuantityDto;
import com.app.ismart.dto.CompetotorImagesDto;
import com.app.ismart.dto.ExpiredItemDto;
import com.app.ismart.dto.FeedBackDto;
import com.app.ismart.dto.FeedbackSubmitDto;
import com.app.ismart.dto.ItemDto;
import com.app.ismart.dto.Pop;
import com.app.ismart.dto.PopSubmitDto;
import com.app.ismart.dto.Products;
import com.app.ismart.dto.QuantityDto;
import com.app.ismart.dto.Response;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.dto.ShopImagesDto;
import com.app.ismart.dto.ShopOptionDto;
import com.app.ismart.dto.ShopStatusDto;
import com.app.ismart.dto.UserModel;
import com.app.ismart.dto.VisitsDto;
import com.app.ismart.fragments.FragmentPop;
import com.app.ismart.rcvbase.RecyclerViewUtils;
import com.app.ismart.realm.RealmController;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.repository.BackdoorQuantityRepository;
import com.app.ismart.realm.repository.ComptitorImagesRepository;
import com.app.ismart.realm.repository.ComptitorProductRepository;
import com.app.ismart.realm.repository.ComptitorQuantityRepository;
import com.app.ismart.realm.repository.ExpiredItemRepository;
import com.app.ismart.realm.repository.FeedbackRepository;
import com.app.ismart.realm.repository.FeedbackSubmitRepository;
import com.app.ismart.realm.repository.PopRepository;
import com.app.ismart.realm.repository.PopSubmitRepository;
import com.app.ismart.realm.repository.ProductRepository;
import com.app.ismart.realm.repository.QuanityRepository;
import com.app.ismart.realm.repository.ShopImagesRepository;
import com.app.ismart.realm.repository.ShopOptionsRepository;
import com.app.ismart.realm.repository.ShopStatusRepository;
import com.app.ismart.realm.repository.ShopsRepository;
import com.app.ismart.realm.repository.VisitsRepository;
import com.app.ismart.realm.specfication.GetAllData;
import com.app.ismart.realm.tables.TableVisits;
import com.app.ismart.rest.APIError;
import com.app.ismart.rest.IRestResponseListner;
import com.app.ismart.restmanagers.CommonUploadManger;
import com.app.ismart.restmanagers.ComptitorProductsManager;
import com.app.ismart.restmanagers.FeedbackManager;
import com.app.ismart.restmanagers.LatestProductManager;
import com.app.ismart.restmanagers.PopManger;
import com.app.ismart.restmanagers.ShopOptionRestManager;
import com.app.ismart.restmanagers.ShopsManager;
import com.app.ismart.restmanagers.UploadQuantityManager;
import com.app.ismart.retrofit.RetrofitClient;
import com.app.ismart.utils.FragmentUtils;
import com.app.ismart.utils.GPSTracker;
import com.app.ismart.utils.InternetConnection;
import com.app.ismart.utils.SessionManager;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IRestResponseListner<List<ShopDto>>, IAsync {
    ActivityMainBinding layoutBinding;
    UserModel userModel;
    List<ShopDto> data = new ArrayList<>();
    ProgressDialog dialog;
    private RealmController realmController;
    ShopsRepository repository;
    ProductRepository productRepository;
    QuanityRepository quanityRepository;
    ShopImagesRepository shopImagesRepository;
    ShopStatusRepository shopStatusRepository;
    FeedbackSubmitRepository feedbackSubmitRepository;
    FeedbackRepository repositoryFeedback;
    PopSubmitRepository popSubmitRepository;
    PopRepository popRepository;
    ShopOptionsRepository shopOptionsRepository;
    ExpiredItemRepository expiredItemRepository;
    BackdoorQuantityRepository backdoorQuantityRepository;
    ComptitorProductRepository comptitorProductRepository;
    ComptitorQuantityRepository comptitorQuantityRepository;
    ComptitorImagesRepository comptitorImagesRepository;
    VisitsRepository visitsRepository;
    List<VisitsDto> result;
    ArrayList<String> dates = new ArrayList<String>();
    ArrayList<String> shopids = new ArrayList<String>();
    ArrayList<String> resultsnew = new ArrayList<String>();
    ArrayList<Integer> visitsResults=new ArrayList<Integer>();

    int quantityUploaded = 0;
    int statusUploaded = 0;
    int feedbackuploaded = 0;
    int popsuploaded = 0;
    int expireduploaded = 0;
    int stockuploaded = 0;
    int comptitorquantityuploaded = 0;
    List<ShopStatusDto> shopStatuses;
    List<FeedbackSubmitDto> feedbacks;
    List<PopSubmitDto> pops;
    List<ExpiredItemDto> expiredItems;
    List<QuantityDto> stocktake;
    List<CompetitorQuantityDto> competitorQuantityDtoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading please wait....");
        dialog.show();
        checklocation();
        setSupportActionBar(layoutBinding.appBar.toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, layoutBinding.drawerLayout, layoutBinding.appBar.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        layoutBinding.drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        layoutBinding.navView.setNavigationItemSelectedListener(this);
        userModel = new SessionManager(MainActivity.this).getSession();
        realmController = RealmController.with(this);
        repository = new ShopsRepository(realmController.getRealm());
        quanityRepository = new QuanityRepository(realmController.getRealm());
        shopImagesRepository = new ShopImagesRepository(realmController.getRealm());
        shopStatusRepository = new ShopStatusRepository(realmController.getRealm());
        feedbackSubmitRepository = new FeedbackSubmitRepository(realmController.getRealm());
        popSubmitRepository = new PopSubmitRepository(realmController.getRealm());
        productRepository = new ProductRepository(realmController.getRealm());
        popRepository = new PopRepository(realmController.getRealm());
        repositoryFeedback = new FeedbackRepository(realmController.getRealm());
        expiredItemRepository = new ExpiredItemRepository(realmController.getRealm());
        backdoorQuantityRepository = new BackdoorQuantityRepository(realmController.getRealm());
        shopOptionsRepository = new ShopOptionsRepository(realmController.getRealm());
        comptitorProductRepository = new ComptitorProductRepository(realmController.getRealm());
        comptitorQuantityRepository = new ComptitorQuantityRepository(realmController.getRealm());
        comptitorImagesRepository = new ComptitorImagesRepository(realmController.getRealm());
        visitsRepository=new VisitsRepository(realmController.getRealm());

        View header = layoutBinding.navView.getHeaderView(0);
        TextView Username = (TextView) header.findViewById(R.id.txtUsername);
        TextView Useremail = (TextView) header.findViewById(R.id.txtEmail);
        Username.setText(userModel.username);
        Useremail.setText(userModel.email);

        // Toast.makeText(this, ""+localStored.size(), Toast.LENGTH_SHORT).show();
        if (InternetConnection.checkConnection(getBaseContext())) {
            new AsyncDispatcher(this);

        } else {
            onErrorResponse(null);


        }
        layoutBinding.appBar.btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<QuantityDto> localStored = quanityRepository.query(new GetAllData());
                shopStatuses = shopStatusRepository.query(new GetAllData());
                feedbacks = feedbackSubmitRepository.query(new GetAllData());
                pops = popSubmitRepository.query(new GetAllData());
                expiredItems = expiredItemRepository.query(new GetAllData());
                stocktake = backdoorQuantityRepository.query(new GetAllData());
                competitorQuantityDtoList = comptitorQuantityRepository.query(new GetAllData());
                quantityUploaded = 0;
                final int size = localStored.size();
                if (size <= 0 && shopStatuses.size() <= 0 && feedbacks.size() <= 0 && pops.size() <= 0 && expiredItems.size() <= 0 && stocktake.size() <= 0 && competitorQuantityDtoList.size() <= 0) {
                    Toast.makeText(MainActivity.this, "No data to upload", Toast.LENGTH_SHORT).show();
                } else if (size <= 0 && shopStatuses.size() > 0) {
                    dialog.setMessage("Uploading please wait....");
                    dialog.show();
                    uploadStatus();
                } else if (size <= 0 && shopStatuses.size() <= 0 && feedbacks.size() > 0) {
                    dialog.setMessage("Uploading please wait....");
                    dialog.show();
                    uploadfeedbacks();
                } else if (size <= 0 && shopStatuses.size() <= 0 && feedbacks.size() <= 0 && expiredItems.size() >= 1) {
                    dialog.setMessage("Uploading please wait....");
                    dialog.show();
                    uploadExpiredItems();
                } else if (size <= 0 && shopStatuses.size() <= 0 && feedbacks.size() <= 0 && expiredItems.size() <= 0 && stocktake.size() >= 1) {
                    dialog.setMessage("Uploading please wait....");
                    dialog.show();
                    uploadstocktake();
                } else if (size <= 0 && shopStatuses.size() <= 0 && feedbacks.size() <= 0 && expiredItems.size() <= 0 && stocktake.size() <= 0 && competitorQuantityDtoList.size() >= 1) {
                    dialog.setMessage("Uploading please wait....");
                    dialog.show();
                    uploadcomptitor();
                } else if (size <= 0 && shopStatuses.size() <= 0 && feedbacks.size() <= 0 && expiredItems.size() <= 0 && stocktake.size() <= 0 && competitorQuantityDtoList.size() <= 0 && pops.size() >= 1) {
                    dialog.setMessage("Uploading please wait....");
                    dialog.show();
                    uploadpops();
                } else {
                    dialog.setMessage("Uploading please wait....");
                    dialog.show();

                    for (QuantityDto quantityDto : localStored) {
                        if (!dates.contains(quantityDto.date)) {
                            dates.add(quantityDto.date);

                        }
                        if (!shopids.contains(quantityDto.shopid)) {

                            shopids.add(quantityDto.shopid);
                        }
                    }
                    for (final String date : dates) {
                        final int totalsize = shopids.size();
                        for (final String shopid : shopids) {
                            String itemsids = null, quantity = null, beforeimg = "", afterimg = "";
                            List<QuantityDto> shopquantities = quanityRepository.queryfordate(new GetAllData(), date, shopid);
                            if (shopquantities.size() >= 1) {
                                for (QuantityDto shopquantity : shopquantities) {
                                    if (itemsids != null) {
                                        itemsids += "," + shopquantity.itemid;
                                    } else {
                                        itemsids = shopquantity.itemid;
                                    }

                                    if (quantity != null) {
                                        quantity += "," + shopquantity.quantity;
                                    } else {
                                        quantity = shopquantity.quantity;
                                    }
                                }
                                List<ShopImagesDto> images = shopImagesRepository.queryForSpecficDate(new GetAllData(), date, shopid);
                                if (images.size() >= 1) {
                                    beforeimg = images.get(0).beforeImage;
                                    afterimg = images.get(0).afterImage;
                                }
                                if (InternetConnection.checkConnection(getBaseContext())) {
                                    IApiCalls api = RetrofitClient.instance.retrofit.create(IApiCalls.class);
                                    Call<Response> apiCall = api.uploadallQuanity(shopid, itemsids, quantity, beforeimg, afterimg);
                                    apiCall.enqueue(new UploadQuantityManager(new IRestResponseListner<Response>() {
                                        @Override
                                        public void onSuccessResponse(Response model) {
                                            Log.d("upload data of", "" + shopid);

                                            quantityUploaded++;
                                            Log.d("upload record", "" + quantityUploaded);
                                            Log.d("total record", "" + totalsize);
                                            if (quantityUploaded == totalsize) {
                                                uploadStatus();
//                                                dialog.dismiss();
//                                                Toast.makeText(MainActivity.this, "Data statusUploaded", Toast.LENGTH_SHORT).show();
                                            }
                                            quanityRepository.removespecfic(new GetAllData(), date, shopid);
                                            shopImagesRepository.removespecfic(new GetAllData(), date, shopid);

                                        }

                                        @Override
                                        public void onErrorResponse(APIError erroModel) {
                                            ;

                                            dialog.dismiss();
                                            Toast.makeText(MainActivity.this, "Data quantity uploading failure,please try again", Toast.LENGTH_SHORT).show();

                                        }
                                    }));
                                } else {
                                    Toast.makeText(MainActivity.this, "No internet available", Toast.LENGTH_SHORT).show();
                                    break;
                                }


                            }
                        }
                    }

                }

            }
        });

//Toast.makeText(this,"Result1:"+visitsResults.size(),Toast.LENGTH_LONG).show();

        //    Realm realm = Realm.getDefaultInstance();

        //   realm.beginTransaction();


        // RealmResults<TableVisits> Mindest = realm.where(TableVisits.class);
      //  int z=0;
      //  for (final String shopid : shopids) {

         //   z++;
      //  }



    }


    private void uploadStatus() {
        statusUploaded = 0;
        final int size = shopStatuses.size();
        if (size <= 0) {
            uploadfeedbacks();
        } else {
            for (final ShopStatusDto shopStatuse : shopStatuses) {
                IApiCalls api = RetrofitClient.instance.retrofit.create(IApiCalls.class);
                Call<Response> apiCall = api.uploadShopStatus(shopStatuse.shopId, shopStatuse.status, shopStatuse.photo, shopStatuse.reason);
                apiCall.enqueue(new CommonUploadManger(new IRestResponseListner<Response>() {
                    @Override
                    public void onSuccessResponse(Response model) {
                        Log.d("upload status data of", "" + shopStatuse.shopId);

                        statusUploaded++;
                        Log.d("upload status record", "" + statusUploaded);
                        Log.d("total staus record", "" + size);
                        if (statusUploaded == size) {

                            uploadfeedbacks();
                            Toast.makeText(MainActivity.this, "Data status Uploaded", Toast.LENGTH_SHORT).show();
                        }
                        shopStatusRepository.removespecfic(new GetAllData(), shopStatuse.date, shopStatuse.shopId);


                    }

                    @Override
                    public void onErrorResponse(APIError erroModel) {
                        ;

                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Data status uploading failure,please try again", Toast.LENGTH_SHORT).show();

                    }
                }));
            }
        }
    }

    private void uploadfeedbacks() {
        feedbackuploaded = 0;
        final int size = feedbacks.size();
        if (size <= 0) {
            uploadExpiredItems();
        } else {
            for (final FeedbackSubmitDto feedbackSubmitDto : feedbacks) {
                IApiCalls api = RetrofitClient.instance.retrofit.create(IApiCalls.class);
                Call<Response> apiCall = api.uploadFeedbacks(feedbackSubmitDto.feedbackId, feedbackSubmitDto.shopId, feedbackSubmitDto.response, feedbackSubmitDto.location);
                apiCall.enqueue(new CommonUploadManger(new IRestResponseListner<Response>() {
                    @Override
                    public void onSuccessResponse(Response model) {
                        Log.d("upload feedback data of", "" + feedbackSubmitDto.shopId);

                        feedbackuploaded++;
                        Log.d("upload feedback record", "" + feedbackuploaded);
                        Log.d("total feedback record", "" + size);
                        if (feedbackuploaded == size) {
                            uploadExpiredItems();

                            Toast.makeText(MainActivity.this, "Data feedback Uploaded", Toast.LENGTH_SHORT).show();
                        }
                        feedbackSubmitRepository.remove(feedbackSubmitDto);


                    }

                    @Override
                    public void onErrorResponse(APIError erroModel) {
                        ;

                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Data feedback uploading failure,please try again", Toast.LENGTH_SHORT).show();

                    }
                }));
            }
        }
    }

    private void uploadstocktake() {
        stockuploaded = 0;
        ArrayList<String> shops = new ArrayList<>();
        final int size = stocktake.size();
        if (size <= 0) {
            uploadcomptitor();
        } else {
            String shopid = null, location = null, time = null, quantity = null, products = null;
            for (final QuantityDto quantityDto : stocktake) {
                if (!shops.contains(quantityDto.shopid)) {
                    shops.add(quantityDto.shopid);
                }


            }
            final int shopssize = shops.size();
            for (final String shop : shops) {
                List<QuantityDto> shopsquantities = backdoorQuantityRepository.queryforshop(new GetAllData(), shop);
                for (QuantityDto shopsquantity : shopsquantities) {
                    if (products != null) {
                        products += "," + shopsquantity.itemid;
                    } else {
                        products = shopsquantity.itemid;
                    }

                    if (quantity != null) {
                        quantity += "," + shopsquantity.quantity;
                    } else {
                        quantity = shopsquantity.quantity;
                    }
                    shopid = shopsquantity.shopid;
                    location = shopsquantity.location;
                    time = shopsquantity.timestamp;
                }


                IApiCalls api = RetrofitClient.instance.retrofit.create(IApiCalls.class);
                Call<Response> apiCall = api.uploadStockTake(quantity, products, shopid, location, time);
                apiCall.enqueue(new CommonUploadManger(new IRestResponseListner<Response>() {
                    @Override
                    public void onSuccessResponse(Response model) {
                        Log.d("upload stocktake  of", "" + shop);

                        stockuploaded++;
                        Log.d("upload stocktake record", "" + stockuploaded);
                        Log.d("total stocktake record", "" + shopssize);
                        if (stockuploaded == shopssize) {
                            uploadcomptitor();

                            Toast.makeText(MainActivity.this, "Data stocktake Uploaded", Toast.LENGTH_SHORT).show();
                        }
                        backdoorQuantityRepository.removeshopAll(shop);


                    }

                    @Override
                    public void onErrorResponse(APIError erroModel) {
                        ;

                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Data stocktake uploading failure,please try again", Toast.LENGTH_SHORT).show();

                    }
                }));
            }
        }
    }

    private void uploadcomptitor() {
        comptitorquantityuploaded = 0;

        if (competitorQuantityDtoList.size() <= 0) {
            uploadpops();
        } else {
            ArrayList<String> displays = new ArrayList<>();
            ArrayList<String> dates = new ArrayList<>();
            ArrayList<String> shops = new ArrayList<>();
            for (CompetitorQuantityDto quantityDto : competitorQuantityDtoList) {
                if (!dates.contains(quantityDto.date)) {
                    dates.add(quantityDto.date);

                }
                if (!displays.contains(quantityDto.displayId)) {

                    displays.add(quantityDto.displayId);
                }
                if (!shops.contains(quantityDto.shopId)) {

                    shops.add(quantityDto.shopId);
                }
            }
            final int size = displays.size();
            for (final String date : dates) {
                for (final String shopid : shops) {
                    for (final String display : displays) {
                        String itemsids = null, quantity = null, afterimg = "";
                        List<CompetitorQuantityDto> itemquantities = comptitorQuantityRepository.queryfordate(new GetAllData(), date, shopid, display);
                        if (itemquantities.size() >= 1) {
                            for (CompetitorQuantityDto shopquantity : itemquantities) {
                                if (itemsids != null) {
                                    itemsids += "," + shopquantity.products;
                                } else {
                                    itemsids = shopquantity.products;
                                }

                                if (quantity != null) {
                                    quantity += "," + shopquantity.quantities;
                                } else {
                                    quantity = shopquantity.quantities;
                                }
                            }
                            List<CompetotorImagesDto> images = comptitorImagesRepository.queryForSpecficDate(new GetAllData(), date, shopid, display);
                            if (images.size() >= 1) {

                                afterimg = images.get(0).afterImage;
                            }
                            final String finalItemsids = itemsids;
                            final String finalQuantity = quantity;
                            final String finalAfterimg = afterimg;
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    IApiCalls api = RetrofitClient.instance.retrofit.create(IApiCalls.class);
                                    Call<Response> apiCall = api.uploadallComptitorQuanity(shopid, finalItemsids, finalQuantity, display, finalAfterimg);
                                    apiCall.enqueue(new CommonUploadManger(new IRestResponseListner<Response>() {
                                        @Override
                                        public void onSuccessResponse(Response model) {
                                            Log.d("upload compitor data of", "" + shopid + " display " + display);

                                            comptitorquantityuploaded++;
                                            Log.d("upload compitor record", "" + comptitorquantityuploaded);
                                            Log.d("total compitor record", "" + size);
                                            if (comptitorquantityuploaded == size) {
                                                uploadpops();

                                                Toast.makeText(MainActivity.this, "Data Competitor Uploaded", Toast.LENGTH_SHORT).show();
                                            }
                                            comptitorQuantityRepository.removespecfic(new GetAllData(), date, display, shopid);
                                            List<CompetotorImagesDto> images = comptitorImagesRepository.query(new GetAllData());
                                            comptitorImagesRepository.removespecfic(new GetAllData(), date, shopid, display);

                                        }

                                        @Override
                                        public void onErrorResponse(APIError erroModel) {
                                            ;

                                            dialog.dismiss();
                                            Toast.makeText(MainActivity.this, "Data Competitor uploading failure,please try again", Toast.LENGTH_SHORT).show();

                                        }
                                    }));

                                }
                            }, 200);

                        }
                    }
                }
            }
        }
    }

    private void uploadpops() {
        popsuploaded = 0;
        final int size = pops.size();
        if (pops.size() <= 0) {
            dialog.dismiss();
        }
        for (final PopSubmitDto popSubmitDto : pops) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    IApiCalls api = RetrofitClient.instance.retrofit.create(IApiCalls.class);
                    Call<Response> apiCall = api.uploadPops(popSubmitDto.popid, popSubmitDto.shopid, popSubmitDto.quantity, popSubmitDto.photo, popSubmitDto.location, popSubmitDto.timestamp);
                    apiCall.enqueue(new CommonUploadManger(new IRestResponseListner<Response>() {
                        @Override
                        public void onSuccessResponse(Response model) {
                            Log.d("upload pops data of", "" + popSubmitDto.shopid);

                            popsuploaded++;
                            Log.d("upload pops record", "" + popsuploaded);
                            Log.d("total pops record", "" + size);
                            if (popsuploaded == size) {
                                dialog.dismiss();

                                Toast.makeText(MainActivity.this, "Data pops Uploaded", Toast.LENGTH_SHORT).show();
                            }
                            popSubmitRepository.remove(popSubmitDto);


                        }

                        @Override
                        public void onErrorResponse(APIError erroModel) {
                            ;

                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "Data pops uploading failure,please try again", Toast.LENGTH_SHORT).show();

                        }
                    }));

                }
            }, 200);

        }

    }

    private void uploadExpiredItems() {
        expireduploaded = 0;
        final int size = expiredItems.size();
        if (expiredItems.size() <= 0) {
            uploadstocktake();
        }
        for (final ExpiredItemDto expiredItemDto : expiredItems) {
            IApiCalls api = RetrofitClient.instance.retrofit.create(IApiCalls.class);
            Call<Response> apiCall = api.uploadExpiredItems(expiredItemDto.location, expiredItemDto.timestamp, expiredItemDto.expired, expiredItemDto.nearexpired, expiredItemDto.itemid, expiredItemDto.shopid);
            apiCall.enqueue(new CommonUploadManger(new IRestResponseListner<Response>() {
                @Override
                public void onSuccessResponse(Response model) {
                    Log.d("upload expireditems  of", "" + expiredItemDto.shopid);

                    expireduploaded++;
                    Log.d("upload expired record", "" + expireduploaded);
                    Log.d("total expired record", "" + size);
                    if (expireduploaded == size) {

                        uploadstocktake();
                        Toast.makeText(MainActivity.this, "Data expired items uploaded", Toast.LENGTH_SHORT).show();
                    }
                    expiredItemRepository.remove(expiredItemDto);


                }

                @Override
                public void onErrorResponse(APIError erroModel) {
                    ;
                    Log.d("expired api error", erroModel.message);
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "Data expired uploading failure,please try again", Toast.LENGTH_SHORT).show();

                }
            }));
        }

    }


    private void setDataAdopter() {
        if (data.size() >= 1) {
            layoutBinding.appBar.mainContent.rcvShops.setVisibility(View.VISIBLE);
            layoutBinding.appBar.mainContent.txtnofound.setVisibility(View.GONE);

            ShopsListAdopter adapter = new ShopsListAdopter(data, MainActivity.this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            layoutBinding.appBar.mainContent.rcvShops.setLayoutManager(RecyclerViewUtils.getGridLayoutManager(getApplicationContext(), 1));
            layoutBinding.appBar.mainContent.rcvShops.setItemAnimator(new DefaultItemAnimator());
            layoutBinding.appBar.mainContent.rcvShops.setAdapter(adapter);
            Paint paint = new Paint();
            paint.setStrokeWidth(5);
            paint.setColor(Color.WHITE);
            paint.setAntiAlias(true);
            paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
            layoutBinding.appBar.mainContent.rcvShops.addItemDecoration(
                    new HorizontalDividerItemDecoration.Builder(MainActivity.this).paint(paint).build());
            adapter.notifyDataSetChanged();
        } else {
            layoutBinding.appBar.mainContent.rcvShops.setVisibility(View.GONE);
            layoutBinding.appBar.mainContent.txtnofound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {


        if (layoutBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            layoutBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {

                //                                GeneralDataModel.appIsRunning = false;
                finish();

            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (item.getItemId() == android.R.id.home) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                this.finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            // Handle the camera action
        } else if (id == R.id.nav_pop) {
            new FragmentUtils(MainActivity.this, new FragmentPop(), R.id.fragContainer);
        } else if (id == R.id.nav_feedback) {
            //  new FragmentUtils(MainActivity.this, new FragmentFeedback(), R.id.fragContainer);
        } else if (id == R.id.nav_logout) {
            if (InternetConnection.checkConnection(getBaseContext())) {
                dialog.setMessage("Logging out....");
                dialog.show();
                IApiCalls api = RetrofitClient.instance.retrofit.create(IApiCalls.class);
                Call<Response> apiCall = api.logout();
                apiCall.enqueue(new CommonUploadManger(new IRestResponseListner<Response>() {
                    @Override
                    public void onSuccessResponse(Response model) {
                        dialog.dismiss();

                        new SessionManager(MainActivity.this).logout();
                        Intent logout = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(logout);
                        finish();

                    }

                    @Override
                    public void onErrorResponse(APIError erroModel) {
                        ;

                        dialog.dismiss();
                        new SessionManager(MainActivity.this).logout();
                        Intent logout = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(logout);
                        finish();

                    }
                }));

            } else {
                new SessionManager(MainActivity.this).logout();
                Intent logout = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(logout);
                finish();


            }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSuccessResponse(List<ShopDto> model) {

        repository.removeAll();
        repository.add(model);
        data = model;
        setDataAdopter();
        IApiCalls api = RetrofitClient.instance.retrofit.create(IApiCalls.class);
        Call<List<Pop>> apiCall = api.getpops();
        apiCall.enqueue(new PopManger(new IRestResponseListner<List<Pop>>() {
            @Override
            public void onSuccessResponse(List<Pop> model) {
                Log.d("response of pops ", +model.size() + "");
                popRepository.removeAll();
                popRepository.add(model);

            }

            @Override
            public void onErrorResponse(APIError erroModel) {
                dialog.dismiss();
            }
        }));
        Call<List<ShopOptionDto>> componentCall = api.getcomponent();
        componentCall.enqueue(new ShopOptionRestManager(new IRestResponseListner<List<ShopOptionDto>>() {
            @Override
            public void onSuccessResponse(List<ShopOptionDto> model) {
                Log.d("response of Component ", +model.size() + "");
                shopOptionsRepository.removeAll();
                shopOptionsRepository.add(model);

            }

            @Override
            public void onErrorResponse(APIError erroModel) {
                dialog.dismiss();
            }
        }));
        Call<List<CompetitorProductsDto>> comptitorProductsCall = api.getcomptitorproducts();
        comptitorProductsCall.enqueue(new ComptitorProductsManager(new IRestResponseListner<List<CompetitorProductsDto>>() {
            @Override
            public void onSuccessResponse(List<CompetitorProductsDto> model) {
                Log.d("comptitor products ", +model.size() + "");
                comptitorProductRepository.removeAll();
                comptitorProductRepository.add(model);

            }

            @Override
            public void onErrorResponse(APIError erroModel) {
                dialog.dismiss();
            }
        }));
        for (final ShopDto shopDto : model) {
            ////////////////////// Testing Code Visits Table /////////////////

            int datasize=data.size();

                result = visitsRepository.queryforVisits(new GetAllData(), shopDto.getId(), 1);


                if (result.size() == 0) {

                //    Toast.makeText(MainActivity.this, "No record", Toast.LENGTH_LONG).show();
                } else {
                    // Toast.makeText(MainActivity.this, ""+result.size(), Toast.LENGTH_LONG).show();
                    for (int t = 0; t < result.size(); t++) {

                        visitsResults.add(result.get(t).getSchedularid());
                  //      Toast.makeText(MainActivity.this, "" + result.get(t).getSchedularid(), Toast.LENGTH_LONG).show();
                    }

            }
            ////////////////// Testing Code Visits Table //////////////////////

            if (InternetConnection.checkConnection(MainActivity.this)) {
                IApiCalls porductapi = RetrofitClient.instance.retrofit.create(IApiCalls.class);
                Call<List<Products>> porductapiCall = porductapi.getProductsLatest(shopDto.getId() + "");
                porductapiCall.enqueue(new LatestProductManager(new IRestResponseListner<List<Products>>() {
                    @Override
                    public void onSuccessResponse(List<Products> model) {
                        Log.d("product of shop ", +shopDto.getId() + " size " + model.size());
                        productRepository.removeshopproduct(shopDto.getId() + "");
                        ArrayList<ItemDto> items = new ArrayList<>();
                        for (Products products : model) {
                            ItemDto item = new ItemDto();
                            item.setDisplay(products.getDisplay());
                            item.setCategory(products.getCategory());
                            item.setTitle(products.getTitle());
                            item.setId(products.getId());
                            item.setShopid(shopDto.getId() + "");
                            item.setImageurl(products.getPlenogram());
                            items.add(item);

                        }
                        productRepository.add(items);
                    }

                    @Override
                    public void onErrorResponse(APIError erroModel) {

                    }
                }));
            } else {
                onErrorResponse(null);
                dialog.dismiss();

            }

        }

        for(int i=0 ; i<visitsResults.size() ; i++){

        //    Toast.makeText(this,"Visits Result:"+visitsResults.get(i),Toast.LENGTH_LONG).show();

        }


     //   Toast.makeText(this,"Result2:"+visitsResults.size(),Toast.LENGTH_LONG).show();


        IApiCalls feedbackapi = RetrofitClient.instance.retrofit.create(IApiCalls.class);
        Call<List<FeedBackDto>> feedbackapiCall = feedbackapi.getfeedbcak();
        feedbackapiCall.enqueue(new FeedbackManager(new IRestResponseListner<List<FeedBackDto>>() {
            @Override
            public void onSuccessResponse(List<FeedBackDto> model) {
                Log.d("response of feedbacks ", "");
                repositoryFeedback.removeAll();
                repositoryFeedback.add(model);
                dialog.dismiss();
            }

            @Override
            public void onErrorResponse(APIError erroModel) {
                Log.d("response of feedbacks ", "" + erroModel.message);
            }
        }));
    }

    @Override
    public void onErrorResponse(APIError erroModel) {
        dialog.dismiss();
        data = repository.query(new GetAllData());
        setDataAdopter();
        if (erroModel != null) {
            Log.d("Api call failure", erroModel.message);
        }
        //  Toast.makeText(this, "Request Failure try again" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void IOnPreExecute() {

    }

    @Override
    public Object IdoInBackGround(Object... params) {



        if (InternetConnection.checkConnection(getBaseContext())) {
            IApiCalls api = RetrofitClient.instance.retrofit.create(IApiCalls.class);
            Call<List<ShopDto>> apiCall = api.getShopsLatest(userModel.useId + "");
            apiCall.enqueue(new ShopsManager(this));



        } else {
            onErrorResponse(null);


        }
        return null;
    }

    @Override
    public void IOnPostExecute(Object result) {

    }

    public void enableshoplist(boolean isenable) {
        layoutBinding.appBar.mainContent.rcvShops.setEnabled(isenable);
        layoutBinding.appBar.mainContent.rcvShops.setClickable(isenable);
        if (isenable) {
            layoutBinding.appBar.mainContent.rcvShops.setVisibility(View.VISIBLE);
        } else {
            layoutBinding.appBar.mainContent.rcvShops.setVisibility(View.GONE);
        }


    }

    public String checklocation() {
        String latlng = null;
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(MainActivity.this, "Location permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).
                        show();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", MainActivity.this.getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 789);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }


        } else {
            GPSTracker gps = new GPSTracker(MainActivity.this);


            if (gps.canGetLocation()) {
                String latitude = String.valueOf(gps.getLatitude());
                String longitude = String.valueOf(gps.getLongitude());
                latlng = latitude + "," + longitude;

            } else {
                gps.showSettingsAlert();
            }
        }
        return latlng;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)

    {
        switch (requestCode) {


            case 1:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {

                        checklocation();


                    }
                } else {

                }


                break;


            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }
}
