package com.example.todo.util;

import static com.example.todo.util.RetrofitConfig.retrofit;

import com.example.todo.api.RetrofitService;
public class Api {
    static public RetrofitService service = retrofit.create(RetrofitService.class);
}
