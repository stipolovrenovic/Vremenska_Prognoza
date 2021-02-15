package com.example.vremenskaprognoza;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity implements
        Callback<Forecast> {

    private DatabaseReference mDatabase;

    Forecast dailyForecast = new Forecast();
    private ArrayList<City> listCities;

    private TextView city;
    private String cityValue;

    private Button openDetails;
    private Button openWeek;

    private ImageButton openOptions;

    private Calendar calendar;

    private TextView forecast;

    private TextView temperature;

    private TextView date;

    private SimpleDateFormat dateFormat;
    private String dateString;

    private String apiKey = "820a34e17d9d98cd5b29d24f15ec32fe";
    private String lang = "hr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        final Bundle extras = getIntent().getExtras();
        cityValue = extras.getString("citySelected");

        city = (TextView) findViewById(R.id.textViewCity);
        city.setText(cityValue);

        date = (TextView) findViewById(R.id.textViewDate);

        calendar = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateString = dateFormat.format(calendar.getTime());

        date.setText(dateString);

        int hours = calendar.get(Calendar.HOUR_OF_DAY) + 1;
        int minutes = calendar.get(Calendar.MINUTE);

        String fullTime;

        if(minutes < 10)
            {
                fullTime = hours + ":0" + minutes;
            }
        else
            {
                fullTime = hours + ":" + minutes;
            }

        updateCityHistory(dateString, fullTime, cityValue);

        forecast = (TextView) findViewById(R.id.textViewForecast);
        temperature = (TextView) findViewById(R.id.textViewTemperature);
        openDetails = (Button) findViewById(R.id.btnDetails);
        openWeek = (Button) findViewById(R.id.btnWeek);
        openOptions = (ImageButton) findViewById(R.id.btnOptions);

        ApiManager.getInstance().service().getForecast(cityValue, apiKey, lang).enqueue(this);


        openDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oDetailsIntent = new Intent(getApplicationContext(), WeatherDetailsActivity.class);
                oDetailsIntent.putExtra("grad", cityValue);

                startActivity(oDetailsIntent);
            }
        });

        openWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oWeekIntent = new Intent(getApplicationContext(), WeeklyForecastActivity.class);
                oWeekIntent.putExtra("grad", cityValue);

                startActivity(oWeekIntent);
            }
        });

        openOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oOptionsIntent = new Intent(getApplicationContext(), OptionsActivity.class);
                startActivity(oOptionsIntent);
            }
        });

    }

    @Override
    public void onResponse(Call<Forecast> call, Response<Forecast> response) {
        if (response.isSuccessful() && response.body() != null){
            dailyForecast = response.body();
            setValues(dailyForecast);
        }

    }

    @Override
    public void onFailure(Call<Forecast> call, Throwable t)
    {
        t.printStackTrace();
    }

    @SuppressLint("DefaultLocale")
    private void setValues(Forecast dailyForecast){
        forecast.setText(dailyForecast.Weather.get(0).getDescription());
        double temperatureCelsius = dailyForecast.main.getTemperature() - 273.15;
        String fullTemperature = String.format("%.2f",temperatureCelsius) + " °C";
        temperature.setText(fullTemperature);
    }

    
    private void updateCityHistory(String date, String time, String cityName)
    {
        City city = new City(date, time, cityName);
        mDatabase.child("cities").push().setValue(city);
    }

}