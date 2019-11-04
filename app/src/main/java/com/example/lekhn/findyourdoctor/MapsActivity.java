package com.example.lekhn.findyourdoctor;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.Manifest;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.lekhn.findyourdoctor.Doctor.Doctor_Profile_Activity;
import com.example.lekhn.findyourdoctor.MapDirection.DirectionFinder;
import com.example.lekhn.findyourdoctor.MapDirection.DirectionFinderListener;
import com.example.lekhn.findyourdoctor.MapDirection.Route;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap mMap;

    String TAG = "GoogleMapActivity";
    LocationHelper.LocationResult locationResult;
    LocationHelper locationHelper;
    ProgressDialog progressDialog;
    LatLng currentMarker,destinationmarker,destinationmarker1;
    String  source,destination,destination1;
    private  List<Marker> originMarkers = new ArrayList<>();
    private  List<Marker> destinationMarkers = new ArrayList<>();
    private  List<Polyline> polylinePaths = new ArrayList<>();

    // LatLng CurrentLatLng;
    LocationManager locationManager;
    String provider;
    double longitude, latitude;

    public static int REQUEST_FOR_ACESS_LOCATION = 123;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (checkLocationPermission()) {
            getLocation();
        }
        else {
            Intent intent = new Intent(this, Doctor_Profile_Activity.class);
            startActivity(intent);
            finish();
            Toast.makeText(this,"Permission Denied by you",Toast.LENGTH_SHORT).show();
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms

                // Add a marker in Sydney and move the camera
                //LatLng sydney = new LatLng(-34, 151);

                Log.d("LatLang : ", latitude + "  " + longitude);

                currentMarker = new LatLng(latitude, longitude);
                source = currentMarker.latitude + "," + currentMarker.longitude;

                destinationmarker = new LatLng(27.6941636,85.3280989);
                destination = destinationmarker.latitude + "," + destinationmarker.longitude;

                destinationmarker1 = new LatLng(27.7084,85.3260);
                destination1 = destinationmarker1.latitude + "," + destinationmarker1.longitude;

                //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.addMarker(new MarkerOptions().position(destinationmarker1).title("Marker in Current"));
                mMap.addMarker(new MarkerOptions().position(destinationmarker).title("Marker in Dest"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(nepal,11f));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinationmarker1, 21));

                FindDirection();
            }
        }, 2000);



    }

    private void FindDirection() {

        try{
            new DirectionFinder((DirectionFinderListener)this,source,destination).execute();

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    private void showPermissionRationaleSnackBar() {
        Snackbar.make(findViewById(R.id.map), "Permission",
                Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Request the permission
                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{READ_EXTERNAL_STORAGE},
                        REQUEST_FOR_ACESS_LOCATION);
            }
        }).show();

    }

    @SuppressLint("LongLogTag")
    public void getLocation() {

        this.locationResult = new LocationHelper.LocationResult() {
            @SuppressLint("LongLogTag")
            @Override
            public void gotLocation(Location location) {

                // Got the location!
                if (location != null) {

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Log.e("Inside getLocation", "lat: " + latitude + ", long: " + longitude);

                    // here you can save the latitude and longitude values
                    // maybe in your text file or database

                } else {
                    Log.d(TAG, "Location is null.");
                }

                Log.d(TAG, "Ended getting user location.");
                locationHelper.stopGettingLocationUpdates();

            }

        };

        // initialize our useful class,
        this.locationHelper = new LocationHelper();

        Log.d(TAG, "Started getting user location.");

        this.locationHelper.getLocation(MapsActivity.this, locationResult);


        //Log.e(TAG, "Ended getting user location.");
        //this.locationHelper.stopGettingLocationUpdates();


    }

    @Override
    public void onDirectionFinderStart() {

        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }

        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {

        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();


        for (Route route : routes) {
            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 13));

//            Toast.makeText(this, "Duration : " + route.duration.text , Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, "Distance : " + route.distance.text, Toast.LENGTH_SHORT).show();

            Log.d("Route Distance: ",route.distance.text);
            Log.d("Route Duration: ",route.duration.text);

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.GREEN).
                    width(8);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));

            List<LatLng> points = route.points; // route is instance of PolylineOptions

            LatLngBounds.Builder bc = new LatLngBounds.Builder();

            for (LatLng item : points) {
                bc.include(item);
            }

            LatLngBounds bounds = bc.build();

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 60);
            mMap.moveCamera(cameraUpdate);

        }


    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission")
                        .setMessage("Update Location")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        locationManager.requestLocationUpdates(provider, 400, 1, (LocationListener) this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
}
