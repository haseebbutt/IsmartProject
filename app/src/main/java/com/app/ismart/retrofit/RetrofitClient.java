package com.app.ismart.retrofit;

import android.content.Context;

import com.app.ismart.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cielowigle on 11/03/2017.
 */

public class RetrofitClient {
    public static RetrofitClient instance;

    public static Retrofit retrofit;
    public RetrofitClient(final Context context) {
        instance=this;



        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();


        Gson gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
               .client(client)
                .baseUrl(context.getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                
                .build();

    }

    public RetrofitClient getInstance(Context context) {
        if (instance == null) {
            new RetrofitClient(context);
        }
        return instance;
    }
}
