package com.example.dominika.naszsamochod;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Dominika on 09.10.2017.
 */

//KLASA ODPOWIADAJACA ZA LOGOWANIE SIE I REJESTRACJE DO SYSTEMU
    //MOZNA LOGOWAC SIE ZA POMOCA FACEBOOK JAK I EMAIL I HASLA

public class LogIn extends AppCompatActivity {

    ////////ZEZWOLENIE NA UZYCIE LOKALIZACJI////////////////////////////////////////
    private static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST=1337;
    ////////////////////////////////////////////////////////////////////////////////

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        final ImageButton LogIn = (ImageButton) findViewById(R.id.logInButton);
        ImageButton facebook_button = (ImageButton) findViewById(R.id.facebook_button);
        ImageButton ragister_button = (ImageButton) findViewById(R.id.register_button);

        EditText email = (EditText) findViewById(R.id.email_ET);
        EditText password = (EditText) findViewById(R.id.password_ET);

        //SPRAWDZENIE ZEZWOLENIA NA UZYCIE LOKALIZACJI
        if (!canAccessLocation()) {
            requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
        }


        //REAKCJA NA KLIKNIECIE PRZYCISKU PRZEKIEROWUJACEGO NA STRONE WEBOWA DO REJESTARCJI
        ragister_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //REAKCJA NA KLIKNIECIE PRZYCISKU LOGOWANIA (TRZEBA WPISAC EMAIL I HASLO)
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this, Menu.class);
                startActivity(intent);
            }
        });


        //REAKCJA NA KLIKNIECIE PRZYCISKU ODPOWIADAJACEGO ZA LOGOWANIE PRZEZ FACEBOOK
        facebook_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    //////////////////LOKALIZACJA-ZEZWOLENIE///////////////////////////////////////////
    private boolean canAccessLocation() {
        return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == checkSelfPermission(perm));
    }
    ///////////////////////////////////////////////////////////////////////////////////
}
