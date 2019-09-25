package com.abcsoft.catalogador.services;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class Localizador {

    private LocationManager locationManager;
    private String providerName;

    public Localizador(){

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        providerName = locationManager.getBestProvider(criteria, false);

        if (providerName != null && !providerName.equals("")) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Log.d("**","No hay permiso");
                return;
            }
            Location location = locationManager.getLastKnownLocation(providerName);

            if (location != null) {
                Log.d("**","Provider: " + providerName);
                Log.d("**","Longitud: " + location.getLongitude());
                Log.d("**","Latitud: " + location.getLatitude());
            } else {
                Log.d("**","No hay provider");
            }

        }
    }
}
