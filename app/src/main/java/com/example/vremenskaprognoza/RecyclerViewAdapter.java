package com.example.vremenskaprognoza;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CityViewHolder> {

    private ArrayList<City> cities;


    public RecyclerViewAdapter(ArrayList<City> cities, OnItemClickListener mOnClickListener) {
        this.cities = cities;
        this.mOnClickListener = mOnClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    final private OnItemClickListener mOnClickListener;


    @Override
    public RecyclerViewAdapter.CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.CityViewHolder holder, int position) {
        City city = cities.get(position);
        holder.textViewDate.setText(city.date);
        holder.textViewTime.setText(city.time);
        holder.textViewCity.setText(city.cityName);
    }

    @Override
    public int getItemCount()
    {
        return cities.size();
    }

    public class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewDate;
        TextView textViewTime;
        TextView textViewCity;

        public CityViewHolder(View itemView) {
            super(itemView);

            textViewDate = itemView.findViewById(R.id.date);
            textViewTime = itemView.findViewById(R.id.time);
            textViewCity = itemView.findViewById(R.id.cityName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickListener.onItemClick(position);
        }
    }
}

