package com.app.ismart.restmanagers;

import com.app.ismart.dto.ShopDto;
import com.app.ismart.dto.User;
import com.app.ismart.rest.IRestResponseListner;
import com.app.ismart.rest.RestBaseManager;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by HP 2 on 5/10/2017.
 */

public class ShopsManager extends RestBaseManager<List<ShopDto>> {
    public ShopsManager(IRestResponseListner iref) {
        super(iref);
    }

    @Override
    protected void onSuccessResponse(List<ShopDto> model) {

    }

    @Override
    protected void onFailureResponse(ResponseBody respBody) {

    }

    @Override
    protected void onFailureResponseException(Throwable t) {

    }
}
