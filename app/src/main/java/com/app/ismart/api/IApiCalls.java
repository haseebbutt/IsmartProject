package com.app.ismart.api;

import com.app.ismart.dto.CompetitorProductsDto;
import com.app.ismart.dto.FeedBackAnswersDto;
import com.app.ismart.dto.FeedBackDto;
import com.app.ismart.dto.ImeiDto;
import com.app.ismart.dto.ItemDto;
import com.app.ismart.dto.Pop;
import com.app.ismart.dto.Products;
import com.app.ismart.dto.Response;
import com.app.ismart.dto.ShopDto;
import com.app.ismart.dto.ShopOptionDto;
import com.app.ismart.dto.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by HP 2 on 5/7/2017.
 */

public interface IApiCalls {


    @POST("login")
    @FormUrlEncoded
    Call<User> getLogin(@Field("username") String username,
                        @Field("password") String password,
                        @Field("IMEI") String IMEI);

    @GET("shops")
    Call<List<ShopDto>> getShops();
    @GET("shops/user/{userId}")
    Call<List<ShopDto>> getShopsLatest(@Path("userId") String id);

    @GET("products")
    Call<List<ItemDto>> getProducts();

    @GET("shop/products/{param}")
    Call<List<Products>> getProductsLatest(@Path("param") String id);

    @POST("merchandising/add")
    @FormUrlEncoded
    Call<Response> uploadQuanity(@Field("quantity") String quantity,
                                 @Field("productId") String productId,
                                 @Field("shopId") String shopId);

    @POST("merchandising/response")
    @FormUrlEncoded
    Call<Response> uploadallQuanity(@Field("shopId") String shopId,
                                    @Field("products") String products,
                                    @Field("quantities") String quantities,
                                    @Field("beforePhoto") String beforePhoto,
                                    @Field("afterPhoto") String afterPhoto,
                                    @Field("display_name") String display_name,
                                    @Field("visitid") String visitid);

    @GET("pop/client")
    Call<List<Pop>> getpops();

    @POST("pop/client/response")
    @FormUrlEncoded
    Call<Response> uploadPopQuanity(
            @Field("itemId") String itemId,
            @Field("quantity") String quantity,
            @Field("userId") String userid);

    @POST("shop/status")
    @FormUrlEncoded
    Call<Response> uploadShopStatus(
            @Field("shopId") String shopId,
            @Field("status") String status,
            @Field("faciaPhoto") String faciaPhoto,
            @Field("reason") String reason,
            @Field("visitId") String visitId);

    @GET("feedback")
    Call<List<FeedBackDto>> getfeedbcak();

    @GET("feedback/answer")
    Call<List<FeedBackAnswersDto>> getfeedbcakAnswer();

    @POST("feedback/response")
    @FormUrlEncoded
    Call<Response> uploadFeedbacks(
            @Field("feedbackId") String feedbackId,
            @Field("shopId") String shopId,
            @Field("response") String response,
            @Field("location") String location,
            @Field("visitId") String visitId);

    @GET("logout")
    Call<Response> logout();

    @POST("pop/shop/response")
    @FormUrlEncoded
    Call<Response> uploadPops(
            @Field("itemId") String itemId,
            @Field("shopId") String shopId,
            @Field("quantity") String quantity,
            @Field("photo") String photo,
            @Field("location") String location,
            @Field("time") String time,
            @Field("visitId") String visitId);

    @POST("expiry/response")
    @FormUrlEncoded
    Call<Response> uploadExpiredItems(
            @Field("location") String location,
            @Field("time") String time,
            @Field("expiredQuantity") String expiredQuantity,
            @Field("nearExpiredQuantity") String nearExpiredQuantity,
            @Field("itemId") String itemId,
            @Field("shopId") String shopId,
            @Field("visitId") String visitId);

    @POST("stocktake/response")
    @FormUrlEncoded
    Call<Response> uploadStockTake(@Field("quantities") String quantities,
                                   @Field("products") String products,
                                   @Field("shopId") String shopId,
                                   @Field("location") String location,
                                   @Field("time") String timestamp,
                                   @Field("visitId") String visitId);

    @GET("drive/{driveId}")
    Call<List<ShopOptionDto>> getcomponent(@Path("driveId") String driveId);

    @GET("competitor/products")
    Call<List<CompetitorProductsDto>> getcomptitorproducts();

    @POST("competitor/response")
    @FormUrlEncoded
    Call<Response> uploadallComptitorQuanity(@Field("shopId") String shopId,
                                             @Field("products") String products,
                                             @Field("quantities") String quantities,
                                             @Field("displayId") String displayId,
                                             @Field("photo") String photo,
                                             @Field("visitId") String visitId);

    @GET("imei/{param}")
    Call<Response> getImei(@Path("param") String imei);


}
