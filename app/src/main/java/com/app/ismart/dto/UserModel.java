package com.app.ismart.dto;

/**
 * Created by HP 2 on 5/8/2017.
 */

public class UserModel {
    public int useId;
    public String username;
    public String password;
    public String email;
    public String avatar;
    public String created_at;
    public String updated_at;
    public boolean isRemember;
    public boolean isLogedIn;

    public int getUseId() {
        return useId;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public boolean isRemember() {
        return isRemember;
    }

    public boolean isLogedIn() {
        return isLogedIn;
    }

    public static UserModel instance;

    public UserModel(User model, UserModel userDto) {
        instance = this;
        this.username = model.getName();
        this.email = model.getEmail();
        this.useId = model.getId();
        this.avatar = model.getAvatar();
        this.created_at = model.getCreatedAt();
        this.updated_at = model.getUpdatedAt();
        this.isLogedIn = userDto.isLogedIn();
        this.isRemember = userDto.isRemember();
        this.password=userDto.getPassword();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public static UserModel getinstance() {
        if (instance == null) {
            new UserModel();
        }

        return instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserModel() {
        instance = this;

    }
}
