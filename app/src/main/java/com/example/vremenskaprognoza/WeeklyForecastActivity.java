package com.example.vremenskaprognoza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeeklyForecastActivity extends AppCompatActivity implements
        Callback<ArrayList<LocationInfo>>{

    private RecyclerView recyclerViewWeekForecast;
    WeekForecast weekForecast = new WeekForecast();
    ArrayList<String> weekDates = new ArrayList<>();
    ArrayList<WeekForecastComplete> forecastsWeek = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ImageButton openOptions;

    private Calendar calendar;

    private SimpleDateFormat dateFormat;

    private String apiKey = "820a34e17d9d98cd5b29d24f15ec32fe";
    private String lang = "hr";

    private String cityValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_forecast);

        final Bundle extras = getIntent().getExtras();
        cityValue = extras.getString("grad");

        dateFormat = new SimpleDateFormat("dd/MM");

        for (int i = 0; i < 7; i++) {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, i);
            String day = dateFormat.format(calendar.getTime());
            weekDates.add(day);
        }

        recyclerViewWeekForecast  = (RecyclerView) findViewById(R.id.recyclerViewWeeklyForecast);
        mLayoutManager = new GridLayoutManager(this, 4);
        recyclerViewWeekForecast.setLayoutManager(mLayoutManager);

        ApiManager.getInstance().service().getCoordinates(cityValue, 1, apiKey).enqueue(this);


        openOptions = (ImageButton) findViewById(R.id.btnOptions2);

        openOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oOptionsIntent = new Intent(getApplicationContext(), OptionsActivity.class);
                startActivity(oOptionsIntent);
            }
        });

    }


    @Override
    public void onResponse(Call<ArrayList<LocationInfo>> call, Response<ArrayList<LocationInfo>> response) {
        float lat = response.body().get(0).lat;
        float lon = response.body().get(0).lon;

        setAdapter(lat, lon);
    }

    @Override
    public void onFailure(Call<ArrayList<LocationInfo>> call, Throwable t) {
        t.printStackTrace();
    }

    public void setAdapter(float latitude, float longitude)
    {
        ApiManager.getInstance().service().getWeekForecast(latitude, longitude, apiKey, lang).enqueue(new Callback<WeekForecast>() {


            @Override
            public void onResponse(Call<WeekForecast> call, Response<WeekForecast> response) {
                weekForecast = response.body();

                for(int i = 0; i < 7; i++)
                {
                    WeekForecastComplete forecast = new WeekForecastComplete(weekForecast.Daily.get(i).Weather.get(0).getDescription(), weekDates.get(i));
                    forecastsWeek.add(forecast);
                }

                mAdapter = new RecyclerViewWeekAdapter(forecastsWeek);
                recyclerViewWeekForecast.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<WeekForecast> call, Throwable t) {
                t.printStackTrace();
            }

        });
    }
}