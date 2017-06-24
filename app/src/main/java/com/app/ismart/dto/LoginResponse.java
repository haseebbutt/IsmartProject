package com.app.ismart.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by HP 2 on 5/9/2017.
 */

public class LoginResponse
{
    private User user;

    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }


}