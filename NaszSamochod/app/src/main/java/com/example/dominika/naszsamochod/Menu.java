package com.example.dominika.naszsamochod;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Dominika on 09.10.2017.
 */


//Klasa obslugujaca menu z opcjami
public class Menu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu);

        Button prices = (Button) findViewById(R.id.price);
        Button reminder = (Button) findViewById(R.id.reminder);
        Button motoDictionary = (Button) findViewById(R.id.motoDictionary);
        Button calendar = (Button) findViewById(R.id.calendar);
        Button priv_message = (Button) findViewById(R.id.priv_message);
        Button group_message = (Button) findViewById(R.id.group_message);
        Button maps = (Button) findViewById(R.id.maps);
        Button poi = (Button) findViewById(R.id.poi);

        //PRZYCISK PRZEKIEROWUJACY DO LAYOUT Z CENAMI PALIW NA POBLISKICH STACJACH BENZYNOWYCH
        prices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Prices.class);
                startActivity(intent);
            }
        });

        //PRZYCISK PRZEKIEROWUJACY DO LAYOUT Z
            // PRZEJECHANYMI KM
            // PRZYPOMNIENIEM O OC/AC
            // PRZYPOMNIENIEM O WYMIANIE OLEJU
            // ZUZYTYCH PALIWEM
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Reminder.class);
                startActivity(intent);
            }
        });

        //PRZYCISK PRZEKIEROWUJACY DO LAYOUT Z MOTOSLOWNIKIEM
        motoDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, MotoDictionary.class);
                startActivity(intent);
            }
        });

        //PRZYCISK PRZEKIEROWUJACY DO LAYOUT Z KALENDARZEM NAPRAW
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Calendar.class);
                startActivity(intent);
            }
        });

        //PRZYCISK PRZEKIEROWUJACY DO LAYOUT Z WIADOMOSCIAMI PRYWATNYMI
        priv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, PrivMessage.class);
                startActivity(intent);
            }
        });

        //RZYCISK PRZEKIEROWUJACY DO LAYOUT Z WIADOMOSCIAMI GRUPOWYMI
        group_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, GroupMessage.class);
                startActivity(intent);
            }
        });

        //PRZYCISK PRZEKIEROWUJACY DO LAYOUT Z REJESTROWANIEM PRZEJECHANEJ TRASY
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        //PRZYCISK PRZEKIEROWUJACY DO LAYOUT Z POI
            // (STACJE, NOCLEGI, WARSZTATY, SKLEPY Z CZESCIAMI, KORKI ULICZNE, PRZEBUDOWY DROG, FOTORADARY?)
        poi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Poi.class);
                startActivity(intent);
            }
        });
    }
}
