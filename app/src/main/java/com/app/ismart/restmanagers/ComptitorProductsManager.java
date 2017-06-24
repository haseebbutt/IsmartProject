package com.app.ismart.restmanagers;

import com.app.ismart.dto.CompetitorProductsDto;
import com.app.ismart.rest.IRestResponseListner;
import com.app.ismart.rest.RestBaseManager;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by Faheem-Abbas on 6/9/2017.
 */

public class ComptitorProductsManager extends RestBaseManager<List<CompetitorProductsDto>> {
    public ComptitorProductsManager(IRestResponseListner iref) {
        super(iref);
    }

    @Override
    protected void onSuccessResponse(List<CompetitorProductsDto> model) {

    }

    @Override
    protected void onFailureResponse(ResponseBody respBody) {

    }

    @Override
    protected void onFailureResponseException(Throwable t) {

    }
}
