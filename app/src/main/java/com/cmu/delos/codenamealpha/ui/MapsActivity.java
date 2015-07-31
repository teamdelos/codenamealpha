package com.cmu.delos.codenamealpha.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;

import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.cmu.delos.codenamealpha.R;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    //private Location mylocation = mMap.getMyLocation();
    //private LatLng sydney = new LatLng(mylocation.getLatitude(),mylocation.getLongitude());

    AppLocationService appLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        appLocationService = new AppLocationService(
                MapsActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        //LatLng coord = new LatLng(mylocation.getLatitude(), mylocation.getLongitude());
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        Location gpsLocation = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);
        if (gpsLocation != null) {
            double mlatitude = gpsLocation.getLatitude();
            double mlongitude = gpsLocation.getLongitude();
            String result = "Latitude: " + gpsLocation.getLatitude() +
                    " Longitude: " + gpsLocation.getLongitude();

            //   mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("I am here"));
            LatLng sydney = new LatLng(mlatitude, mlongitude);
            mMap.addMarker(new MarkerOptions().position(sydney).title("You are here."));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));


            //LatLng coord;
            //mMap.addMarker(new MarkerOptions().position(coord).title("Marker"));

        }
    }

    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney, Australia, and move the camera.
        // Location mylocation = mMap.getMyLocation();
        //LatLng coord = new LatLng(mylocation.getLatitude(), mylocation.getLongitude());
        Location gpsLocation = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);
        if (gpsLocation != null) {
            double mlatitude = gpsLocation.getLatitude();
            double mlongitude = gpsLocation.getLongitude();
            String result = "Latitude: " + gpsLocation.getLatitude() +
                    " Longitude: " + gpsLocation.getLongitude();

            //   mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("I am here"));
            LatLng sydney = new LatLng(mlatitude, mlongitude);
            mMap.addMarker(new MarkerOptions().position(sydney).title("You are here."));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12));
            //mMap.moveCamera(CameraUpdateFactory.zoomTo(12));

        }

    }
}
