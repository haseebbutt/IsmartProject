package com.app.ismart.restmanagers;

import com.app.ismart.dto.Products;
import com.app.ismart.rest.IRestResponseListner;
import com.app.ismart.rest.RestBaseManager;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by Faheem-Abbas on 5/31/2017.
 */

public class LatestProductManager  extends RestBaseManager<List<Products>> {
    public LatestProductManager(IRestResponseListner iref) {
        super(iref);
    }

    @Override
    protected void onSuccessResponse(List<Products> model) {

    }

    @Override
    protected void onFailureResponse(ResponseBody respBody) {

    }

    @Override
    protected void onFailureResponseException(Throwable t) {

    }
}
