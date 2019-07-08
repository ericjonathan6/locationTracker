package com.testproject

import android.Manifest
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import com.facebook.react.ReactActivity
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Abghi, 8-7-2019
 */
class MainActivity: ReactActivity() {

    private val rxPermissions by lazy { RxPermissions(this) }
    private val compositeDisposable = CompositeDisposable()
    private val locationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                val lat = result?.lastLocation?.latitude ?: 0.0
                val long = result?.lastLocation?.longitude ?: 0.0
                Log.d("Current Location", "Position Lat:$lat Long:$long")
            }

            override fun onLocationAvailability(availability: LocationAvailability?) {

            }
        }
    }

    private var isRequestingLocation = false

    companion object {
        private const val REQUESTING_LOCATION_UPDATES_KEY = "REQUESTING_LOCATION_UPDATES_KEY"
    }

    override fun getMainComponentName(): String? = "testProject"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isRequestingLocation = savedInstanceState?.getBoolean(REQUESTING_LOCATION_UPDATES_KEY) ?: false

        if (rxPermissions.isGranted(Manifest.permission.ACCESS_COARSE_LOCATION)
                && rxPermissions.isGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            LocationManager.start(locationCallback)
        } else {
            requestLocationPermission()
        }
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        LocationManager.stop(locationCallback)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, isRequestingLocation)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    private fun requestLocationPermission() {
        val disposable = rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe { granted ->
                    if (granted) {
                        LocationManager.start(locationCallback)
                    }
                }

        compositeDisposable.add(disposable)
    }
}