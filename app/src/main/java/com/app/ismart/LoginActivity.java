package com.app.ismart;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import com.app.ismart.api.IApiCalls;
import com.app.ismart.databinding.ActivityLoginBinding;
import com.app.ismart.dto.User;
import com.app.ismart.dto.UserModel;
import com.app.ismart.rest.APIError;
import com.app.ismart.rest.IRestResponseListner;
import com.app.ismart.restmanagers.LoginManager;
import com.app.ismart.retrofit.RetrofitClient;
import com.app.ismart.utils.InternetConnection;
import com.app.ismart.utils.SessionManager;

import retrofit2.Call;

public class LoginActivity extends AppCompatActivity implements IRestResponseListner<User> {
    ActivityLoginBinding layoutBinding;
    SessionManager sessionManager;
    UserModel userDto;
    ProgressDialog dialog;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    String IMEI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        layoutBinding.edtUsername.setText("LHR001");
        layoutBinding.edtpassword.setText("lahore");
        sessionManager = new SessionManager(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Login please wait....");
        userDto = sessionManager.getSession();
        loadIMEI();
        if (userDto.isLogedIn) {
            if (InternetConnection.checkConnection(getBaseContext())) {
                dialog.show();
                IApiCalls api = RetrofitClient.instance.retrofit.create(IApiCalls.class);
                Call<User> apiCall = api.getLogin(userDto.email, userDto.password,IMEI);
                apiCall.enqueue(new LoginManager(LoginActivity.this));
            } else {
                sessionManager.createSession(userDto);
                Intent next = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(next);
                finish();


            }

        }
//        if (userDto.isRemember) {
//
//            layoutBinding.edtUsername.setText(userDto.username);
//            layoutBinding.edtpassword.setText(userDto.password);
//        }
        if (userDto == null) {
            userDto = new UserModel();
        }
        layoutBinding.chkRememberme.setChecked(userDto.isRemember);
        layoutBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = layoutBinding.edtUsername.getText().toString();
                String password = layoutBinding.edtpassword.getText().toString();
                if (userName.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
                    Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                } else {

                    userDto.setUsername(userName);
                    userDto.setPassword(password);
                    userDto.isRemember = layoutBinding.chkRememberme.isChecked();
                    userDto.isLogedIn = true;

                    dialog.show();
                    IApiCalls api = RetrofitClient.instance.retrofit.create(IApiCalls.class);
                    Call<User> apiCall = api.getLogin(userName, password,IMEI);
                    apiCall.enqueue(new LoginManager(LoginActivity.this));

                }
            }
        });
    }

    @Override
    public void onSuccessResponse(User model) {
        dialog.dismiss();
        if (model.getName() != null) {
            sessionManager.createSession(new UserModel(model, userDto));
            Intent next = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(next);
            finish();
        } else {
            Toast.makeText(this, "Invalid username/password", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onErrorResponse(APIError erroModel) {
        dialog.dismiss();
        if (erroModel != null) {
            Toast.makeText(this, "Request Failure try again", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No internet available", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadIMEI() {
        // Check if the READ_PHONE_STATE permission is already available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // READ_PHONE_STATE permission has not been granted.
            requestReadPhoneStatePermission();
        } else {
            // READ_PHONE_STATE permission is already been granted.
            doPermissionGrantedStuffs();
        }
    }


    /**
     * Requests the READ_PHONE_STATE permission.
     * If the permission has been denied previously, a dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private void requestReadPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Permission Request")
                    .setMessage("IMEI required for login")
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //re-request
                            ActivityCompat.requestPermissions(LoginActivity.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
                        }
                    })

                    .show();
        } else {
            // READ_PHONE_STATE permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_PHONE_STATE) {
            // Received permission result for READ_PHONE_STATE permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // READ_PHONE_STATE permission has been granted, proceed with displaying IMEI Number
                //alertAlert(getString(R.string.permision_available_read_phone_state));
                doPermissionGrantedStuffs();
            } else {
                alertAlert("Permission required to get IMEI");
            }
        }
    }

    private void alertAlert(String msg) {
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Permission Request")
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do somthing here
                    }
                })

                .show();
    }


    public void doPermissionGrantedStuffs() {
        //Have an  object of TelephonyManager
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //Get IMEI Number of Phone  //////////////// for this example i only need the IMEI
        IMEI = tm.getDeviceId();

    }

}
