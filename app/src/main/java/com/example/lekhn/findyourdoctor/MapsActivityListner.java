package com.example.lekhn.findyourdoctor;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by Lekhn on 2/12/2018.
 */

public class MapsActivityListner implements LocationListener {
    public static Location location;
    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
