package com.app.ismart.rest;




import retrofit2.Response;

/**
 * Created by UmerKiani on 1/31/2016.
 */
public interface IRestResponseListner<T> {
    public void onSuccessResponse(T model);

    public void onErrorResponse(APIError erroModel);


}
