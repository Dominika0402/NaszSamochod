package com.example.dominika.naszsamochod;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dominika on 10.10.2017.
 */



//ADAPTER DLA KLASY PRICES
public class PricesAdapter extends RecyclerView.Adapter<PricesAdapter.ViewHolder> {

    List<String> placesList;
    List<String> list95;
    List<String> list98;

    public static RecyclerView recyclerView;

    private static final String TAG = "PriceAdapter";


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final View mView;

        public final TextView place;
        public final TextView petrol95;
        public final TextView petrol98;
        public final RecyclerView recyclerView;

        private String mItem;

        public void setItem(String item) {
            mItem = item;
            petrol95.setText(item);
            petrol98.setText(item);
            place.setText(item);
        }

        public ViewHolder(View view) {
            super(view);
            mView = view;
            place = (TextView) view.findViewById(R.id.place);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_prices_petrol);
            petrol95 = (TextView) view.findViewById(R.id.price95);
            petrol98 = (TextView) view.findViewById(R.id.price98);
            view.setOnClickListener(this);
        }

        //KLIKANIE NA STACJE I WYPISYWANIE W KONSOLI
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick " + getPosition() + " " + mItem);
            System.out.println(mItem);
        }
    }


    public PricesAdapter(List<String> places_list, List<String> list_95, List<String> list_98)
    {
        placesList = places_list;
        list95 = list_95;
        list98 = list_98;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prices_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setItem(placesList.get(position));
        holder.setItem(list95.get(position));
        holder.setItem(list98.get(position));
    }


    @Override
    public int getItemCount() {
        return placesList.size();
    }
}

