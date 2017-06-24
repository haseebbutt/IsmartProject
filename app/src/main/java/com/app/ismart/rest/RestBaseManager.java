package com.app.ismart.rest;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by UmerKiani on 1/31/2016.
 */
public abstract class RestBaseManager<T> implements Callback<T> {

    protected IRestResponseListner<T> iref;

    public RestBaseManager(IRestResponseListner iref) {
        this.iref = iref;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        int respCode = response.code();

        T model = response.body();
        if (model != null && respCode == 200) {

            onSuccessResponse(model);

            if (iref != null) {
                iref.onSuccessResponse(model);
            }
        } else {
            ResponseBody responseBody = response.errorBody();

            if (responseBody != null) {
                new AsyncResponseErrorParser(responseBody, iref);
            }
        }

    }


    protected abstract void onSuccessResponse(T model);


    /**
     * This Method Runs on Ui Thread, therefore you need to be sure you parse the data on a background thread
     *
     * @param respBody
     */
    protected abstract void onFailureResponse(ResponseBody respBody);

    /**
     * This Method Runs on Ui Thread, therefore you need to be sure you parse the data on a background thread
     *
     * @param t
     */
    protected abstract void onFailureResponseException(Throwable t);

    @Override
    public void onFailure(Call<T> call, Throwable t) {

        new AsyncExceptionErrorParser(t, iref);
    }


}
