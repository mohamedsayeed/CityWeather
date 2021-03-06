package com.ibs.cityweather.api;

/**
 * Created by Mohamed Sayed on 9/2/2017.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibs.cityweather.model.Demo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionManager {

    static final String API_KEY = "44ee1a8f1bfa0d60fadfd3ad61a6f781";
    private static ConnectionManager connectionManager;
    String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private WeatherClient weatherClient;

    private ConnectionManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        weatherClient = retrofit.create(WeatherClient.class);

    }

    public static ConnectionManager getInstance() {
        if (connectionManager == null)
            connectionManager = new ConnectionManager();

        return connectionManager;
    }

    public Call<Demo> getCityWeather(String cityName) {
        return weatherClient.weatherForCities(cityName, API_KEY);
    }


}
