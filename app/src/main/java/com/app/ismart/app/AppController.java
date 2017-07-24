package com.app.ismart.app;

import android.app.Application;
import android.content.Context;

import com.app.ismart.R;
import com.app.ismart.async.DynamicAsyncTask;
import com.app.ismart.async.IAsync;
import com.app.ismart.retrofit.RetrofitClient;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;


/**
 * Created by cielowigle on 11/03/2017.
 */

public class AppController extends Application implements IAsync {
    private static AppController mInstance;

    private static com.nostra13.universalimageloader.core.ImageLoader loader;

    public static synchronized AppController getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {

        super.onCreate();
      //  Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());


        mInstance = this;
        new DynamicAsyncTask(this).execute();
        new RetrofitClient(getBaseContext());


        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }


    @Override
    public void onTerminate() {
        super.onTerminate();


//        AppEventsLogger.deactivateApp(this);
    }


    @Override
    protected void attachBaseContext(Context base) {

        super.attachBaseContext(base);
//
    }


    @Override
    public void IOnPreExecute() {

    }

    @Override
    public Object IdoInBackGround(Object... params) {
        getUniversalImageLoaderInstance();
        return null;
    }

    @Override
    public void IOnPostExecute(Object result) {

    }

    public static com.nostra13.universalimageloader.core.ImageLoader getUniversalImageLoaderInstance() {
        if (loader == null) {
            DisplayImageOptions defaultOptions =
                    new DisplayImageOptions.Builder()
                            .cacheInMemory(true)
                            .cacheOnDisk(true)
                            .showImageForEmptyUri(R.drawable.ic_menu_camera)
                            .showImageOnLoading(R.drawable.ic_menu_camera)
                            .build();
            ImageLoaderConfiguration mImageLoaderConfig =
                    new ImageLoaderConfiguration.Builder(mInstance.getApplicationContext())
                            .defaultDisplayImageOptions(defaultOptions)
                            .build();


            loader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
            loader.init(mImageLoaderConfig);

        }
        return loader;
    }


    public static com.nostra13.universalimageloader.core.ImageLoader getUniversalImageLoaderInstance(int imageStub) {
        if (loader == null) {


            DisplayImageOptions defaultOptions =
                    new DisplayImageOptions.Builder()
                            .cacheInMemory(true)
                            .cacheOnDisk(true)
                            .showImageForEmptyUri(R.drawable.loadingimage)
                            .showImageOnLoading(R.drawable.loadingimage)
                            .displayer(new FadeInBitmapDisplayer(500))
                            .build();
            ImageLoaderConfiguration mImageLoaderConfig =
                    new ImageLoaderConfiguration.Builder(mInstance.getApplicationContext())
                            .defaultDisplayImageOptions(defaultOptions)
                            .build();


            loader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
            loader.init(mImageLoaderConfig);

        }
        return loader;
    }
}
