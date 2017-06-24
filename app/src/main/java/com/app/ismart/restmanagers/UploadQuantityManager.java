package com.app.ismart.restmanagers;

import com.app.ismart.dto.Response;
import com.app.ismart.rest.IRestResponseListner;
import com.app.ismart.rest.RestBaseManager;

import okhttp3.ResponseBody;

/**
 * Created by Faheem-Abbas on 5/12/2017.
 */

public class UploadQuantityManager extends RestBaseManager<Response> {
    public UploadQuantityManager(IRestResponseListner iref) {
        super(iref);
    }

    @Override
    protected void onSuccessResponse(Response model) {

    }

    @Override
    protected void onFailureResponse(ResponseBody respBody) {

    }

    @Override
    protected void onFailureResponseException(Throwable t) {

    }
}
