package com.lajotasoftware.goservice.retrofit;

import com.google.gson.Gson;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private Retrofit retrofit;
    private Integer i;

    public RetrofitService() {
        initializeRetrofit();
    }

    private void initializeRetrofit() {
        i = 1;
        if (i==0){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.111:8080")
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build();
        }else if (i==1){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://138.97.217.167:8080")
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build();
        }
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
