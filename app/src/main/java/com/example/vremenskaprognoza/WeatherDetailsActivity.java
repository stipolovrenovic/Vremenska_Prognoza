package com.example.vremenskaprognoza;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherDetailsActivity extends AppCompatActivity implements
        Callback<Forecast> {

    Forecast dailyForecast = new Forecast();

    private TextView temperature;
    private TextView tempMin;
    private TextView tempMax;
    private TextView humidity;

    private ImageButton openOptions;

    private String sCity;

    private Calendar calendar;

    private TextView date;

    private SimpleDateFormat dateFormat;
    private String dateString;

    private String apiKey = "820a34e17d9d98cd5b29d24f15ec32fe";
    private String lang = "hr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        final Bundle extras = getIntent().getExtras();
        sCity = extras.getString("grad");

        date = (TextView) findViewById(R.id.textViewDateValue);

        calendar = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateString = dateFormat.format(calendar.getTime());

        date.setText(dateString);

        temperature = (TextView) findViewById(R.id.textViewTemp);
        tempMin = (TextView) findViewById(R.id.textViewMin);
        tempMax = (TextView) findViewById(R.id.textViewMax);
        humidity = (TextView) findViewById(R.id.textViewHumid);

        openOptions = (ImageButton) findViewById(R.id.btnOptions1);

        ApiManager.getInstance().service().getForecast(sCity, apiKey, lang).enqueue(this);

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
    public void onFailure(Call<Forecast> call, Throwable t) {
        Toast.makeText(WeatherDetailsActivity.this, t.toString(), Toast.LENGTH_LONG).show();
        t.printStackTrace();
    }

    @SuppressLint("SetTextI18n")
    private void setValues(Forecast dailyForecast){
        String tempCelsius = convertToCelsius(dailyForecast.main.getTemperature());
        temperature.setText(tempCelsius);

        String minCelsius =  convertToCelsius(dailyForecast.main.getTempMin());
        tempMin.setText(minCelsius);

        String maxCelsius =  convertToCelsius(dailyForecast.main.getTempMax());
        tempMax.setText(maxCelsius);

        humidity.setText(dailyForecast.main.getHumidity().toString());
    }

    public String convertToCelsius(float temp)
    {
        double celsius = temp - 273.15;
        @SuppressLint("DefaultLocale") String fullTemp = String.format("%.2f", celsius) + " °C";

        return fullTemp;
    }
}