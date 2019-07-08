package com.testproject;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;

public class LocationManager {
    private static LocationTrackerService locationTrackerService;

    public static void init(Context context) {
        locationTrackerService = new LocationTrackerService(
                LocationServices.getFusedLocationProviderClient(context)
        );
    }

    public static void start(LocationCallback locationCallback) {
        Log.d("Tracking uhuy", "start track");
        locationTrackerService.startTrackLocation(locationCallback);
    }

    public static void stop(LocationCallback locationCallback) {
        Log.d("Tracking uhuy", "stop track");
        locationTrackerService.stopTrackLocation(locationCallback);
    }

    public static String getLocation() {
        return "";
    }
}