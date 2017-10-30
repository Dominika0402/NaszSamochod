package com.example.dominika.naszsamochod;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;


public class Maps extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location location;
    private double latitiude;   //szerokosc
    private double longitude;   //dlugosc
    private double latE;
    private double lonE;
    private double distE;
    ImageButton b2, b3, b4;
    boolean start = false;
    boolean stop = false;
    MyCount counter;
    int n;
    Long s1 = 0L;
    Long r1;
    TextView e1, e2;
    Button button4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);              //WYRZUCA NULL

        b2 = (ImageButton) findViewById(R.id.start); //start moving.. calculates distance on clicking this
        b3 = (ImageButton) findViewById(R.id.pause); //pause
        b4 = (ImageButton) findViewById(R.id.stop); //stop
        e1 = (TextView) findViewById(R.id.timer);   //timer
        e2 = (TextView) findViewById(R.id.distance); //counter - km
        button4 = (Button) findViewById(R.id.button4);

        //KLIKNIECIE START - ROZPOCZECIE OBLICZANIA DLUGOSCI PRZEBYTEJ TRASY
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start = true;
                stop = false;
                if(s1==0L) {
                    counter = new MyCount(30000, 1000);
                    counter.start();
                }
                else {
                    counter = new MyCount(s1, 1000);
                    counter.start();
                }
            }
        });

        //KLIKNIECIE PAUSA - ZATRZYMANIE OBLICZANIA DLUGOSCI PRZEBYTEJ TRASY
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start = false;
                counter.cancel();
            }
        });

        //KLIKNIECIE STOP - ZATRZYMANIE OBLICZANIA DLUGOSCI PRZEBYTEJ TRASY I WYZEROWANIE LICZNIKA KM
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop = true;
                distE = 0.0;
                counter.cancel();
                s1 = 0L;
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearch(view);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);


    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }




    //DOBRZE OKRESLA LOKALIZACJE
    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            //mMarker = mMap.addMarker(new MarkerOptions().position(loc));
            if (mMap != null) {
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                longitude = location.getLongitude();
                latitiude = location.getLatitude();

                if(start == true && stop == false) {
                    if (latE == 0.0 && lonE == 0.0) {
                        latE = latitiude;
                        lonE = longitude;
                    }
                    if (latitiude != latE && longitude != lonE) {
                        distE += getDistance(latE, lonE, latitiude, longitude);
                        latE = latitiude;
                        lonE = longitude;
                        String value = String.valueOf(distE);
                        e2.setText(value.substring(0, Math.min(value.length(), 5)) + "m");
                        //Toast.makeText(Maps.this, "Distance: " + String.valueOf(distE), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(Maps.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(Maps.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);
    }


    //OBLICZANIE DYSTANSU MIEDZY DWOMA PUNKTAMI
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


    //ZAMIANA NA RADIANY
    private double degToRad(double degrees)
    {
        return degrees * (Math.PI / 180);
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


    //WYSZUKIWANIE LOKALIZACJI PO WSPISANIU SLOWA KLUCZOWEGO - NIE UWZGLEDNIA AKTUALNEGO POLOZENIA
    public void onSearch(View view)
    {
        EditText location = (EditText) findViewById(R.id.adress);
        String location2 = location.getText().toString();
        List<Address> addressList = null;

        if(location!=null || !location.equals(""))
        {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location2,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("MARKER"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        }
    }
}