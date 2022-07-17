package com.example.todo.util;

import android.util.Log;

import com.example.todo.api.NullOnEmptyConverterFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                Request request = chain.request();
                okhttp3.Response response = chain.proceed(request);

                if(response.isSuccessful()) return response;

                // Error 처리
                // todo deal with the issues the way you need

                Util.alertMessage("Error", response.message());

                Log.i("OkHttp", "Error Handling");

                return response;
            })
            .build();

    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.35.168:8080/api/")
            .client(okHttpClient)
            .addConverterFactory(new NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
