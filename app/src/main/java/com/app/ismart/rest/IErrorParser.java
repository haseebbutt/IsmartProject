package com.app.ismart.rest;




import okhttp3.ResponseBody;

/**
 * Created by UmerKiani on 1/31/2016.
 */
public interface IErrorParser {


    public APIError parseExceptionError(Throwable t);

    public APIError parseRestCallError(ResponseBody body);
}
