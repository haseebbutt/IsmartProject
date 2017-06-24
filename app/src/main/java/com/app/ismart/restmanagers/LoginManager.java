package com.app.ismart.restmanagers;

import com.app.ismart.dto.LoginResponse;
import com.app.ismart.dto.User;
import com.app.ismart.rest.IRestResponseListner;
import com.app.ismart.rest.RestBaseManager;


import okhttp3.ResponseBody;

/**
 * Created by cielowigle on 20/03/2017.
 */

public class LoginManager extends RestBaseManager<User> {
    public LoginManager(IRestResponseListner iref) {
        super(iref);
    }

    @Override
    protected void onSuccessResponse(User model) {

    }

    @Override
    protected void onFailureResponse(ResponseBody respBody) {

    }

    @Override
    protected void onFailureResponseException(Throwable t) {

    }


}
