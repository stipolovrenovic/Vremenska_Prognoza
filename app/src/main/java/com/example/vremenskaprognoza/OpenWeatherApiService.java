package com.example.vremenskaprognoza;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface OpenWeatherApiService {

    @GET("data/2.5/weather")
    Call<Forecast> getForecast(
            @Query("q") String cityName,
            @Query("appid") String key,
            @Query("lang") String lang
    );

    @GET("geo/1.0/direct")
    Call<ArrayList<LocationInfo>> getCoordinates(
            @Query("q") String cityName,
            @Query("limit") int limit,
            @Query("appid") String key
    );

    @GET("data/2.5/onecall")
    Call<WeekForecast> getWeekForecast(
            @Query("lat") float latitude,
            @Query("lon") float longitude,
            @Query("appid") String key,
            @Query("lang") String lang
    );
}
