package com.example.dominika.naszsamochod;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominika on 10.10.2017.
 */

public class MotoDictionary extends AppCompatActivity {

    public static List<String> wordsList = new ArrayList<>();
    public MotoDictionaryAdapter motoDictionaryAdapter;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.moto_dictionary);
        wordsList.add("Amortyzator");
        wordsList.add("Olej");
        wordsList.add("Opona");

        context = getApplicationContext();
        final ListView listView = (ListView) findViewById(R.id.words_list);

        motoDictionaryAdapter = new MotoDictionaryAdapter(context, wordsList);
        listView.setAdapter(motoDictionaryAdapter);
    }
}
