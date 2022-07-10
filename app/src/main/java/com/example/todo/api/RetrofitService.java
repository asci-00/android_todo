package com.example.todo.api;

import com.example.todo.dto.Login;
import com.example.todo.dto.Todo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {
    @POST("users")
    Call<Login.Response> login(@Body Login.Request body);
    @GET("users/{id}")
    Call<Login.Response> getUser(@Path("id") Integer id);
    @GET("users/{id}/todos")
    Call<List<Todo.Response>> getTodos(@Path("id") Integer id);
    @DELETE("todos/{id}")
    Call<Void> deleteTodo(@Path("id") Integer id);
}
