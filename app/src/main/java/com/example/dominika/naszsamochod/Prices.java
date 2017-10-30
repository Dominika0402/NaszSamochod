package com.example.dominika.naszsamochod;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Dominika on 09.10.2017.
 */

public class Prices extends Activity {

    public PricesAdapter adapter;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static final String[] chatRow = {"Audi", "BMW", "Bentley", "Daewoo", "Dacia", "Citroen", "Ford",
            "Fiat", "Ferrari", "Honda", "Hyundai", "Lancia", "Lexus", "Mercedes", "Seat", "Skoda", "Porsche", "Renault",
            "Toyota", "Suzuki", "Subaru", "Volvo", "Volkswagen", "Peugeot", "Opel", "Nissan", "Maserati",
            "Mazda", "Mitsubishi", "Rolls-Royce", "Lamborgini", "Infiniti", "Isuzu", "Iveco", "Chrystel"};
    public static final String[] chatRow2 = {"Audi", "BMW", "Bentley", "Daewoo", "Dacia", "Citroen", "Ford",
            "Fiat", "Ferrari", "Honda", "Hyundai", "Lancia", "Lexus", "Mercedes", "Seat", "Skoda", "Porsche", "Renault",
            "Toyota", "Suzuki", "Subaru", "Volvo", "Volkswagen", "Peugeot", "Opel", "Nissan", "Maserati",
            "Mazda", "Mitsubishi", "Rolls-Royce", "Lamborgini", "Infiniti", "Isuzu", "Iveco", "Chrystel"};
    public static final String[] chatRow3 = {"Audi", "BMW", "Bentley", "Daewoo", "Dacia", "Citroen", "Ford",
            "Fiat", "Ferrari", "Honda", "Hyundai", "Lancia", "Lexus", "Mercedes", "Seat", "Skoda", "Porsche", "Renault",
            "Toyota", "Suzuki", "Subaru", "Volvo", "Volkswagen", "Peugeot", "Opel", "Nissan", "Maserati",
            "Mazda", "Mitsubishi", "Rolls-Royce", "Lamborgini", "Infiniti", "Isuzu", "Iveco", "Chrystel"};

    //PRZEKONWERTOWANIE ARRAY DO LIST
    List chat_row = Arrays.asList(chatRow);
    List chat_row2 = Arrays.asList(chatRow2);
    List chat_row3 = Arrays.asList(chatRow3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prices);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_prices_petrol);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        mRecyclerView.setAdapter(new PricesAdapter(chat_row, chat_row2, chat_row3));
    }
}
