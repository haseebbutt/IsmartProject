package com.app.ismart;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.ismart.databinding.ActivitySplashBinding;
import com.app.ismart.dto.ImeiDto;
import com.app.ismart.realm.RealmController;
import com.app.ismart.realm.repository.ImeiRepository;
import com.app.ismart.realm.repository.ShopsRepository;
import com.app.ismart.realm.specfication.GetAllData;
import com.app.ismart.realm.tables.TableImei;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
 //   ActivitySplashBinding layoutBinding;

    private static String URL1 ="";
    StringRequest request;
    private RequestQueue requestQueue;
    private static int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    String deviceId="";
    RealmController realmController;
    ImeiRepository imeiRepo;
    List<ImeiDto> imeiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(this.TELEPHONY_SERVICE);
//        String deviceId=telephonyManager.getDeviceId();

        realmController = RealmController.with(this);
        imeiRepo = new ImeiRepository(realmController.getRealm());


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      //  layoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash_new);
        setContentView(R.layout.activity_splash_new);

        loadIMEI();



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
            new AlertDialog.Builder(SplashActivity.this)
                    .setTitle("Permission Request")
                    .setMessage("IMEI required for login")
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //re-request
                            ActivityCompat.requestPermissions(SplashActivity.this,
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
        new AlertDialog.Builder(SplashActivity.this)
                .setTitle("Permission Request")
                .setMessage(msg)
                .setCancelable(true)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do somthing here
                    }
                })

                .show();
    }


    public void doPermissionGrantedStuffs() {
        //Have an  object of TelephonyManager
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(this.TELEPHONY_SERVICE);
        deviceId=telephonyManager.getDeviceId();
        displaySplash();
    }


    public void displaySplash() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                imeiList = imeiRepo.query(new GetAllData());

                if (imeiList.size() != 0) {

                    if(imeiList.get(0).getImei().equals(deviceId)){
                        Intent login = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(login);
                        finish();
                        Toast.makeText(SplashActivity.this, "Loading ...", Toast.LENGTH_LONG).show();
                    }


                } else {


                  //  Toast.makeText(SplashActivity.this, "" + deviceId, Toast.LENGTH_LONG).show();

                    URL1 = "http://173.212.235.106:81/api/imei/" + deviceId;

                    requestQueue = Volley.newRequestQueue(getApplicationContext());

                    request = new StringRequest(Request.Method.GET, URL1, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {


                            try {

                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");

                                if (message.equals("un-authorised")) {

                                    Toast.makeText(SplashActivity.this, "App Exiting .. " + message, Toast.LENGTH_LONG).show();

                                    finish();
                                } else {

                                    ImeiDto imeiDto=new ImeiDto();
                                    imeiDto.setImei(deviceId);
                                  //  Toast.makeText(SplashActivity.this, "Dto:"+imeiDto.getImei(), Toast.LENGTH_LONG).show();

                                    imeiRepo.add(imeiDto);
                                    Toast.makeText(SplashActivity.this, "Loading ...Please Wait", Toast.LENGTH_LONG).show();
                                    Intent login = new Intent(SplashActivity.this, LoginActivity.class);
                                    startActivity(login);
                                    finish();

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(SplashActivity.this, "" + error, Toast.LENGTH_LONG).show();
                        }
                    });

                    requestQueue.add(request);
                }
                //Do something after 100ms
            } // End Else

        }, 5000);
    }




}
