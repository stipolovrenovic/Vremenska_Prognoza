package com.example.vremenskaprognoza;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeekForecast{
    @SerializedName("daily")
    public ArrayList<daily> Daily = new ArrayList<>();


    public class daily
    {

        @SerializedName("weather")
        public ArrayList<weather> Weather = new ArrayList<>();


        public class weather
        {
            @SerializedName("description")
            private String description;

            public String getDescription()
            {
                return description;
            }
        }
    }
}
