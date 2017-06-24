package com.app.ismart.rest;


import com.app.ismart.async.DynamicAsyncTask;
import com.app.ismart.async.IAsync;

/**
 * Created by UmerKiani on 1/31/2016.
 */
public class AsyncExceptionErrorParser implements IAsync {


    private final Throwable responseBody;

    private IRestResponseListner<?> iref;
    private IErrorParser iError = new GsonErrorParser();


    public AsyncExceptionErrorParser(Throwable responseBody, IRestResponseListner<?> iref) {

        this.iref = iref;

        this.responseBody = responseBody;
        new DynamicAsyncTask(this).execute();
    }

    @Override
    public void IOnPreExecute() {

    }

    @Override
    public Object IdoInBackGround(Object... params) {
        APIError error = iError.parseExceptionError(responseBody);

        return error;
    }

    @Override
    public void IOnPostExecute(Object result) {
        if(result!=null){
            APIError error = (APIError) result;
            if(iref!=null){
                iref.onErrorResponse(error);
            }
        }

    }
}
