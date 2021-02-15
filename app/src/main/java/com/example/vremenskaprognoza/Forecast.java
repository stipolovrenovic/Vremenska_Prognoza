package com.example.vremenskaprognoza;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Forecast {

    @SerializedName("weather")
    public ArrayList<weather> Weather = new ArrayList<>();

    @SerializedName("main")
    public main main;

    public class weather
    {
        @SerializedName("description")
        private String description;

        public String getDescription()
        {
            return description;
        }
    }

    public class main
    {
        @SerializedName("temp")
        private Float temperature;

        @SerializedName("temp_min")
        private Float tempMin;

        @SerializedName("temp_max")
        private Float tempMax;

        @SerializedName("humidity")
        private Float humidity;


        public Float getTemperature()
        {
            return temperature;
        }

        public Float getTempMin()
        {
            return tempMin;
        }

        public Float getTempMax()
        {
            return tempMax;
        }

        public Float getHumidity()
        {
            return humidity;
        }
    }




}
