package com.testproject

import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

/**
 * Created by Abghi, 8-7-2019
 */
class LocationTrackerService(private val locationProviderClient: FusedLocationProviderClient) {

    companion object {
        /**
         * The desired interval for location updates. Inexact. Updates may be more or less frequent.
         */
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 3000

        /**
         * The fastest rate for active location updates. Updates will never be more frequent
         * than this value.
         */
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2
    }



    private val locationRequest by lazy {
        LocationRequest().apply {
            interval = UPDATE_INTERVAL_IN_MILLISECONDS
            fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

   fun startTrackLocation(locationCallback: LocationCallback?) {
        try {
            locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        } catch (ex: SecurityException) {
            Log.e("Security Exception", ex.message.orEmpty())
        }

    }

    fun stopTrackLocation(locationCallback: LocationCallback?) {
        locationProviderClient.removeLocationUpdates(locationCallback)
    }
}