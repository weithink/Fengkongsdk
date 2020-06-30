package com.weithink.fengkong.api;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;


import com.weithink.fengkong.WeithinkFactory;
import com.weithink.fengkong.bean.LocationInfo;

import java.util.ArrayList;
import java.util.List;


public class LocationInfoApi {
    private static LocationInfoApi instance;
    private List<LocationInfo> locationInfoList;
    private String type;
    private LocationManager locationManager;

    public static LocationInfoApi getInstance() {
        if (instance == null) {
            synchronized (LocationInfoApi.class) {
                if (instance == null) {
                    instance = new LocationInfoApi();
                }
            }
        }
        return instance;
    }

    public List<LocationInfo> getLocationInfoList() {
        return this.locationInfoList;
    }

    private String getType() {
        return this.type;
    }

    private void setType(String type) {
        this.type = type;
    }

    public void getLocation(Context context) {
        if (this.locationInfoList == null) {
            this.locationInfoList = new ArrayList<>();
        } else {
            this.locationInfoList.clear();
        }
        this.locationManager = (LocationManager) context.getSystemService("location");
        if (this.locationManager == null) {
            setType("-1");
            packageNullLocation(1);
            return;
        }
        List<String> providers = this.locationManager.getProviders(true);
        if (providers != null) {
            for (int i = 0; i < providers.size(); i++) {
                String locationProvider, provider = providers.get(i);
                if ("network".equals(provider)) {
                    locationProvider = "network";
                    setType("00");
                } else if ("gps".equals(provider)) {
                    locationProvider = "gps";
                    setType("01");
                } else if ("passive".equals(provider)) {
                    locationProvider = "passive";
                    setType("02");
                } else {
                    continue;
                }
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_COARSE_LOCATION") != 0 &&
                            ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                        WeithinkFactory.getLogger().debug("No location permission");
                        return;
                    }
                    Location location = this.locationManager.getLastKnownLocation(locationProvider);
                    if (location != null) {
                        WeithinkFactory.getLogger().debug("getLastKnownLocation ======");
                        packageLocation(location);
                    } else {
                        packageNullLocation(2);
                    }
                }
                continue;
            }
        } else {
            packageNullLocation(3);
        }
    }

    private void packageLocation(Location location) {
        LocationInfo locationInfo = new LocationInfo();
        String longitude = Location.convert(location.getLongitude(), 0);
        String latitude = Location.convert(location.getLatitude(), 0);
        String accuracy = String.valueOf(location.getAccuracy());
        locationInfo.setLongitude(longitude);
        locationInfo.setLatitude(latitude);
        locationInfo.setAccuracy(accuracy);
        locationInfo.setLocationType(getType());
        this.locationInfoList.add(locationInfo);
    }

    private void packageNullLocation(int nullType) {
        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setLongitude("0.0");
        locationInfo.setLatitude("0.0");
        if (nullType == 1) {
            locationInfo.setAccuracy("locationManager is null,pls check location permission!");
        } else if (nullType == 2) {
            locationInfo.setAccuracy("location is null,pls move the location and get it again!");
        } else if (nullType == 3) {
            locationInfo.setAccuracy("providers is null,pls check location permission!");
        }
        locationInfo.setLocationType(getType());
        this.locationInfoList.add(locationInfo);
    }
}
