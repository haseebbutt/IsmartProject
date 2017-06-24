package com.app.ismart.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.ismart.dto.UserModel;

/**
 * Created by HP 2 on 4/23/2017.
 */

public class SessionManager {
    Context context;
    SharedPreferences sharedPreferences;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("ismart", Context.MODE_PRIVATE);
    }
    public void createSession(UserModel userDto){
        SharedPreferences.Editor edt = sharedPreferences.edit();
        edt.putString("username", userDto.username);
        edt.putInt("userid", userDto.useId);
        edt.putString("password", userDto.password);
        edt.putString("email", userDto.email);
        edt.putBoolean("isRemember", userDto.isRemember);
        edt.putBoolean("isLoged", userDto.isLogedIn);
        edt.commit();


    }
    public UserModel getSession(){
        UserModel userDto=new UserModel();
        userDto.username =sharedPreferences.getString("username", "");
        userDto.password=sharedPreferences.getString("password", "");
        userDto.email=sharedPreferences.getString("email", "");
        userDto.isRemember=sharedPreferences.getBoolean("isRemember", false);
        userDto.isLogedIn=sharedPreferences.getBoolean("isLoged",false);
        userDto.useId=sharedPreferences.getInt("userid", 0);
        return userDto;
    }
    public void logout(){
        SharedPreferences.Editor edt = sharedPreferences.edit();
        edt.putBoolean("isLoged", false);
        edt.commit();
    }
}
