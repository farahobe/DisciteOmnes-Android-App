package com.example.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.0.148:8080/";

    private static RetrofitClient instance;
    private DisciteOmnesApi api;

    // Privater Konstruktor für das Singleton-Muster
    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(DisciteOmnesApi.class);
    }

    // Singleton-Instanz-Methode
    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    // Methode zum Abrufen der API-Schnittstelle
    public DisciteOmnesApi getApi() {
        return api;
    }
}
