package com.app.ismart.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.Window;
import android.widget.Button;

import com.app.ismart.R;

/**
 * Created by ANQ on 8/8/2016.
 */

public class GPSTracker extends Service implements LocationListener {

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 3000; // 3 seconds
    private final Context mContext;
    // Declaring a Location Manager
    protected LocationManager locationManager;
    // flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude


    public GPSTracker(Context context) {
        this.mContext = context;

        getLocation();
    }

    @SuppressWarnings("ResourceType")
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                //
//                Toast.makeText(mContext,"no network provider is enabled",Toast.LENGTH_SHORT).show();
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {

                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        return longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingsAlert() {
        if (mContext != null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

            alertDialog.setTitle("GPS Setting");

            alertDialog
                    .setMessage("GPS is not enabled. Do you want to go to settings menu?");

            alertDialog.setPositiveButton("Settings",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            mContext.startActivity(intent);
                        }
                    });

            alertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            AlertDialog dialog = alertDialog.create();

// Make some UI changes for AlertDialog
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(final DialogInterface dialog) {

                    // Add or create your own background drawable for AlertDialog window
                    Window view = ((AlertDialog)dialog).getWindow();
                  //  view.setBackgroundDrawableResource(R.color.colorPrimary);

                    // Customize POSITIVE, NEGATIVE and NEUTRAL buttons.
                    Button positiveButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                    positiveButton.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                    positiveButton.setTextColor(Color.BLACK);
                    positiveButton.setTypeface(Typeface.DEFAULT_BOLD);
                    positiveButton.invalidate();

                    Button negativeButton = ((AlertDialog)dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
                    negativeButton.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                    negativeButton.setTypeface(Typeface.DEFAULT_BOLD);
                    negativeButton.setTextColor(Color.BLACK);
                    negativeButton.invalidate();


                }
            });
            dialog.show();
        }
    }

    public void stopUsingGPS() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                 TODO: Consider calling
//                    ActivityCompat#requestPermissions
//                 here to request the missing permissions, and then overriding
//                   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                                          int[] grantResults)
//                 to handle the case where the user grants the permission. See the documentation
//                 for ActivityCompat#requestPermissions for more details.
                ActivityCompat.requestPermissions((Activity)mContext, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 789);

            }
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

}