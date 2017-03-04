package ru.mail.aslanisl.allinone.weatherFragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;

public class LocationService {

    private static final long LOCATION_UPDATE_MIN_TIME = 600000L; // 10 min
    private static final float LOCATION_UPDATE_MIN_DISTANCE = 100.0f; // meters

    private Activity activity;

    private android.location.LocationManager mLocationManager;
    private Location mLocation; // currentLocation

    public LocationService(Activity activity) {
        this.activity = activity;
    }

    /*
     * LOCATION RELATED CODE
     */

    public void getLocation() {
        if (mLocationManager == null) {
            mLocationManager = (android.location.LocationManager) activity.getSystemService(LOCATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(activity.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            getLoc();
        }
    }

    public void getLoc() {
        try {
            Location last = mLocationManager.getLastKnownLocation(android.location.LocationManager.NETWORK_PROVIDER);
            if (last != null) {
                onLocationReceived(last);
            } else {
                last = mLocationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
                if (last != null) {
                    onLocationReceived(last);
                }
            }
        } catch (SecurityException e) {
            Log.d(TAG, "Unable to get last location");
        }

        try {
            mLocationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER,
                    LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
            mLocationManager.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER,
                    LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener);
        } catch (SecurityException e) {
            Log.d(TAG, "Unable to request location updates");
        }
    }

    public final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            onLocationReceived(location);
            try {
                mLocationManager.removeUpdates(mLocationListener);
            } catch (SecurityException ex) {
                Log.d(TAG, "Unable to remove location updates");
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    public void onLocationReceived(Location location) {
        mLocation = location;
        /*
         * LOGGING //TODO REMOVE IN RELEASE
         */
    }

    public void onStop () {
        if (mLocationManager != null) {
            if (ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mLocationManager.removeUpdates(mLocationListener);
        }
    }

    public Location getmLocation() {
        return mLocation;
    }
}
