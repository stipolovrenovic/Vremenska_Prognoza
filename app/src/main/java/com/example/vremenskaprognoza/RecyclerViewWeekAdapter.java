package com.example.vremenskaprognoza;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewWeekAdapter extends RecyclerView.Adapter<RecyclerViewWeekAdapter.WeekViewHolder>{

    private ArrayList<WeekForecastComplete> forecasts;

    public RecyclerViewWeekAdapter(ArrayList<WeekForecastComplete> forecasts) {
        this.forecasts = forecasts;
    }

    @Override
    public RecyclerViewWeekAdapter.WeekViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewforecast_item, parent, false);
        return new WeekViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewWeekAdapter.WeekViewHolder holder, int position) {
        WeekForecastComplete forecast = forecasts.get(position);

        holder.textViewDay.setText(forecast.getDate());
        holder.textViewForecast.setText(forecast.getForecast());;
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }


    class WeekViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDay;
        TextView textViewForecast;

        public WeekViewHolder(View itemView) {
            super(itemView);

            textViewDay = itemView.findViewById(R.id.dayDate);
            textViewForecast = itemView.findViewById(R.id.dayForecast);
        }
    }
}
