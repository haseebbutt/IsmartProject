package com.app.ismart.rest;


import com.google.gson.GsonBuilder;


import okhttp3.ResponseBody;

/**
 * Created by UmerKiani on 1/31/2016.
 */
public class GsonErrorParser implements IErrorParser {


    @Override
    public APIError parseExceptionError(Throwable t) {
        APIError error;
        if (t != null) {
            t.printStackTrace();
            error = new APIError();
            error.statusCode = t.hashCode();
            error.message = t.getMessage();


            return error;
        }

        return null;
    }

    @Override
    public APIError parseRestCallError(ResponseBody body) {
        APIError error;
        try {
            String errorBody = body.string();
            if (errorBody != null) {
                error = new GsonBuilder().create().fromJson(errorBody, APIError.class);
                return error;
            }

        } catch (Exception e) {
            e.printStackTrace();
            error = new APIError();

            error.message = e.getMessage();
            error.statusCode = e.hashCode();
            return error;

        }
        return null;
    }
}
