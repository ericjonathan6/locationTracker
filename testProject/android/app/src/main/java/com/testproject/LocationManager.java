package com.testproject;

public class LocationManager {
    private static LocationService locationService;

    public static void init() {
        locationService = new LocationService();
    }

    public static void start() {

    }

    public static void stop() {

    }

    public static String getLocation() {

    }
}