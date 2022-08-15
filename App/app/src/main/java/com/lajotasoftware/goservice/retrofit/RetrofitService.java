package com.lajotasoftware.goservice.retrofit;

import com.google.gson.Gson;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private Retrofit retrofit;

    public RetrofitService() {
        initializeRetrofit();
    }

    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
        .baseUrl("http://192.168.0.100:9090")
        .addConverterFactory(GsonConverterFactory.create(new Gson()))
        .build();
        /*retrofit = new Retrofit.Builder()
        .baseUrl("http://0.tcp.sa.ngrok.io:10944")
        .addConverterFactory(GsonConverterFactory.create(new Gson()))
        .build();*/
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
