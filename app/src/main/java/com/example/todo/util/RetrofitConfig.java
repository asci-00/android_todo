package com.example.todo.util;

import com.example.todo.api.NullOnEmptyConverterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.35.203:8080/api/")
            .addConverterFactory(new NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
