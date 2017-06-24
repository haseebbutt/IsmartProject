package com.app.ismart.restmanagers;

import com.app.ismart.dto.FeedBackDto;
import com.app.ismart.dto.Pop;
import com.app.ismart.rest.IRestResponseListner;
import com.app.ismart.rest.RestBaseManager;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Created by Faheem-Abbas on 5/25/2017.
 */

public class FeedbackManager extends RestBaseManager<List<FeedBackDto>> {
    public FeedbackManager(IRestResponseListner iref) {
        super(iref);
    }

    @Override
    protected void onSuccessResponse(List<FeedBackDto> model) {

    }

    @Override
    protected void onFailureResponse(ResponseBody respBody) {

    }

    @Override
    protected void onFailureResponseException(Throwable t) {

    }
}
