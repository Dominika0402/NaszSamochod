package com.example.dominika.naszsamochod;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dominika on 24.10.2017.
 */

public class MotoDictionaryAdapter extends ArrayAdapter<List> {
    private final Context context;
    private final List<String> words;

    public MotoDictionaryAdapter(Context context, List<String> words) {
        super(context,0);
        this.context = context;
        this.words = words;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.moto_dictionary_row, parent, false);
        }

        TextView word = (TextView) convertView.findViewById(R.id.word);

        word.setTextColor(Color.parseColor("#999999"));


        word.setText(words.get(position));

        return convertView;
    }
}