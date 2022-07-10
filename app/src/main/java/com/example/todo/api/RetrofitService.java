package com.example.todo.api;

import com.example.todo.dto.Login;
import com.example.todo.dto.Todo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitService {
    @POST("users")
    Call<Login.Response> login(@Body Login.Request body);
    @GET("users/{id}")
    Call<Login.Response> getUser(@Path("id") Integer id);
    @GET("users/{id}/todos")
    Call<ArrayList<Todo.Response>> getTodos(@Path("id") Integer id);

    @POST("users/{id}/todos")
    Call<Todo.Response> createTodo(@Path("id") Integer id, @Body Todo.Request body);
    @PUT("todos/{id}")
    Call<Todo.Response> updateTodo(@Path("id") Integer id, @Body Todo.Request body);
    @DELETE("todos/{id}")
    Call<Void> deleteTodo(@Path("id") Integer id);

}
