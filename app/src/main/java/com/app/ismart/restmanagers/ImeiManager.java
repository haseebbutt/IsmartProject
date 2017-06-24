package com.app.ismart.restmanagers;

import com.app.ismart.dto.ImeiDto;
import com.app.ismart.dto.Response;
import com.app.ismart.rest.IRestResponseListner;
import com.app.ismart.rest.RestBaseManager;

import okhttp3.ResponseBody;

/**
 * Created by HP on 6/20/2017.
 */

public class ImeiManager extends RestBaseManager<Response> {
public ImeiManager(IRestResponseListner iref) {
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
