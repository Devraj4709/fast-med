package com.example.dev.devraj_22;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Pharmacy_location extends AppCompatActivity {

    TextView address,longitude,latitude,country,city;

    FusedLocationProviderClient fusedLocationProviderClient;
    Button get_location;
    private final static int REQUEST_CODE=100;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        address=findViewById(R.id.address);
        longitude =findViewById(R.id.longitude);
        latitude =findViewById(R.id.latitude);
        country =findViewById(R.id.country);
        city =findViewById(R.id.city);
        get_location =findViewById(R.id.locationBtn);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            get_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    geolocation();
                }


            });


    } private void geolocation() {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(Pharmacy_location.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            latitude.setText("Latitude  " + addresses.get(0).getLatitude());
                            longitude.setText("Longitude  " + addresses.get(0).getLongitude());
                            city.setText("City  " + addresses.get(0).getLocality());
                            address.setText("Address  " + addresses.get(0).getAddressLine(0));
                            country.setText("Country  " + addresses.get(0).getCountryName());


                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }

        else {
           askpermission();
        }
    }

    private void askpermission() {

        ActivityCompat.requestPermissions(Pharmacy_location.this,new String []
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE  )
        {
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                geolocation();
            }
            else {
                Toast.makeText(this, "Required permission", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
