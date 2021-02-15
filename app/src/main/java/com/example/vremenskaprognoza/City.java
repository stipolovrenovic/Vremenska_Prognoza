package com.example.vremenskaprognoza;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class City {

    public String date, time, cityName;


    public City() {
    }

    public City(String date, String time, String cityName)
    {
        this.date = date;
        this.time = time;
        this.cityName = cityName;
    }

}
