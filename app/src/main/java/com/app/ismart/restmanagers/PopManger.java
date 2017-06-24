package com.app.ismart.restmanagers;

import com.app.ismart.dto.Pop;
import com.app.ismart.rest.IRestResponseListner;
import com.app.ismart.rest.RestBaseManager;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by Faheem-Abbas on 5/21/2017.
 */

public class PopManger extends RestBaseManager<List<Pop>> {
    public PopManger(IRestResponseListner iref) {
        super(iref);
    }

    @Override
    protected void onSuccessResponse(List<Pop> model) {

    }

    @Override
    protected void onFailureResponse(ResponseBody respBody) {

    }

    @Override
    protected void onFailureResponseException(Throwable t) {

    }
}
