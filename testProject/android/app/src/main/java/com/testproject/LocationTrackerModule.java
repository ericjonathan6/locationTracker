package com.testproject;

import android.app.Activity;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.Map;
import java.util.HashMap;

public class LocationTrackerModule extends ReactContextBaseJavaModule {
    private Activity mActivity = null;

    public LocationTrackerModule(ReactApplicationContext reactContext) {

        super(reactContext);
        this.mActivity = reactContext.getCurrentActivity();
    }

    @Override
    public String getName() {
        return "LocationTracker";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        return constants;
    }

    @ReactMethod
    public void show() {

    }

    @ReactMethod
    public void stop() {

    }

    @ReactMethod
    public void read(Callback callback) {
        callback.invoke("wololo");
    }
}