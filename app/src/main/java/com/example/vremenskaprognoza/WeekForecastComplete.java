package com.example.vremenskaprognoza;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class WeekForecastComplete {
    private String forecast;
    private String date;

    public WeekForecastComplete()
    {}

    public String getForecast()
    {
        return forecast;
    }

    public String getDate()
    {
        return date;
    }

    public WeekForecastComplete(String forecast, String date)
    {
        this.forecast = forecast;
        this.date = date;
    }
}
