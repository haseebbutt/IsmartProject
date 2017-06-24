package com.app.ismart.rest;

public class APIError {

    public int statusCode;
    public String message;
    public String cause;

    public APIError(){

    }
    private Error error;

    public void setError(Error error){
        this.error = error;
    }

    public Error getError(){

        if(error == null){
            error = new Error();
        }

        return error;
    }

    @Override
    public String toString(){
        return
                "ErrorCodes{" +
                        "error = '" + error + '\'' +
                        "}";
    }


}