package com.example.dominika.naszsamochod;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMyLocationButtonClickListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 30000;
    protected Location location;
    protected LocationManager locationManager;
    static double n = 0;
    Long s1, r1;
    double plat, plon, clat, clon, dis;
    //dis = dystans,
    //clat = szerokosc geograficzna
    //clon = dlugosc geograficzna
    MyCount counter;
    Thread t1;
    TextView e1;
    boolean bool = true;
    LocationManager manager;
    LocationListener listener;
    ImageButton b2, b3, b5, b1, b4;
    private GoogleMap mMap;
    boolean start = true;

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        b1 = (ImageButton) findViewById(R.id.current); //current position;
        b2 = (ImageButton) findViewById(R.id.start); //start moving.. calculates distance on clicking this
        b3 = (ImageButton) findViewById(R.id.pause); // pause
        b4 = (ImageButton) findViewById(R.id.resume); // resume
        b5 = (ImageButton) findViewById(R.id.getdistanse); // get distance
        e1 = (TextView) findViewById(R.id.co_ordinates);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);              //WYRZUCA NULL


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(MapsActivity.this, "DZIALA", Toast.LENGTH_LONG).show();
                //co.append("\n Elo: " + location.getLatitude()+ " " + location.getAltitude());
            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }
            @Override
            public void onProviderEnabled(String s) {
            }
            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                        },10);
                return;
            }
        }
        else
        {
            configureButton();
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MINIMUM_TIME_BETWEEN_UPDATES,
                    MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                    new MyLocationListener()
            );
        }


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCurrentLocation();
            }
        });

        /////??????????????????????????????????????????????????
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                t1 = new Thread();
                t1.start();
                counter = new MyCount(30000, 1000);
                counter.start();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            while(start){
                                clat=location.getLatitude();
                                clon=location.getLongitude();
                                if(clat!=plat || clon!=plon){
                                    plat=clat;
                                    plon=clon;
                                    dis+=getDistance(plat,plon,clat,clon);
                                }
                            }
                        }
                    }
                }).start();
            }
        });


        ////?????????????????????????????????????????????????????????????????????
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter.cancel();
                bool = false;
                start = false;
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = new MyCount(s1, 1000);
                counter.start();
                bool = true;
            }
        });

        //WYPISANIE PRZEBYTEJ ODLEGLOSCI, CZASU JAZDY, PREDKOSCI
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double time = n * 30 + r1;
                Toast.makeText(MapsActivity.this, "distance in metres:" + String.valueOf(dis) + "Velocity in m/sec :" + String.valueOf(dis / time) + "Time :" + String.valueOf(time), Toast.LENGTH_LONG).show();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    private void configureButton() {
        manager.requestLocationUpdates("gps",5000,0,listener);
    }


    protected void showCurrentLocation() {
        if (location != null) {
            String message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            clat = location.getLatitude();
            clon = location.getLongitude();
            Toast.makeText(MapsActivity.this, message,
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MapsActivity.this, "null location",
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle bundle) {


    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    private class MyLocationListener implements LocationListener {

        //DZIALA
        public void onLocationChanged(Location location) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );

            Toast.makeText(MapsActivity.this, message, Toast.LENGTH_LONG).show();
        }
        //DZIALA
        public void onStatusChanged(String s, int i, Bundle b) {
            Toast.makeText(MapsActivity.this, "Provider status changed",
                    Toast.LENGTH_LONG).show();
        }
        //DZIALA
        public void onProviderDisabled(String s) {
            Toast.makeText(MapsActivity.this,
                    "Provider disabled by the user. GPS turned off",
                    Toast.LENGTH_LONG).show();
        }
        //DZIALA
        public void onProviderEnabled(String s) {
            Toast.makeText(MapsActivity.this,
                    "Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_LONG).show();
        }
    }

    public class MyCount extends CountDownTimer {
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            counter = new MyCount(30000, 1000);
            counter.start();
            n = n + 1;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            s1 = millisUntilFinished;
            r1 = (30000 - s1) / 1000;
            e1.setText("TIME: " + String.valueOf(r1));
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMyLocationButtonClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            switch(requestCode)
            {
                case 10:
                    if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        configureButton();
                    return;
            }
    }

    @Override
    public void onLocationChanged(Location location) {
        String message = String.format(
                "New Location \n Longitude: %1$s \n Latitude: %2$s",
                location.getLongitude(), location.getLatitude()
        );

        Toast.makeText(MapsActivity.this, message, Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onMyLocationButtonClick() {

        Toast.makeText(this, "Current location:\n", Toast.LENGTH_LONG).show();
        return false;
    }


    //ZAPETLA SIE
    /*@Override
    public void run() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            while(start){
                clat=location.getLatitude();
                clon=location.getLongitude();
                if(clat!=plat || clon!=plon){
                    plat=clat;
                    plon=clon;
                    dis+=getDistance(plat,plon,clat,clon);
                }
            }
        }
    }*/

    public double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371.0;

        double latA = degToRad(lat1);
        double lonA = degToRad(lon1);
        double latB = degToRad(lat2);
        double lonB = degToRad(lon2);

        double dLat = latB - latA;
        double dLon = lonB - lonA;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = R * c * 1000;
        return dist;
    }


    private static double degToRad(double degrees)
    {
        return degrees * (Math.PI / 180);
    }
}
