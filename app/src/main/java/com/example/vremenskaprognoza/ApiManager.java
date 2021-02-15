package com.example.vremenskaprognoza;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    static ApiManager instance;
    private OpenWeatherApiService service;

    private ApiManager(){
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder.baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(OpenWeatherApiService.class);
    }

    public static ApiManager getInstance(){
        if (instance == null){
            instance = new ApiManager();
        }
        return instance;
    }

    public OpenWeatherApiService service(){
        return service;
    }

}
