package com.app.ismart.restmanagers;

import com.app.ismart.dto.ShopOptionDto;
import com.app.ismart.rest.IRestResponseListner;
import com.app.ismart.rest.RestBaseManager;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by Faheem-Abbas on 6/6/2017.
 */

public class ShopOptionRestManager extends RestBaseManager<List<ShopOptionDto>> {
    public ShopOptionRestManager(IRestResponseListner iref) {
        super(iref);
    }

    @Override
    protected void onSuccessResponse(List<ShopOptionDto> model) {

    }

    @Override
    protected void onFailureResponse(ResponseBody respBody) {

    }

    @Override
    protected void onFailureResponseException(Throwable t) {

    }
}
