package com.example.todo.activity;

import static com.example.todo.util.Api.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.R;
import com.example.todo.dto.Login;
import com.example.todo.util.Store;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);

        service
                .getTodos(Store.getInstance().getUserId())
                .enqueue(new Callback<Login.Response>() {
//            @Override
//            public void onResponse(Call<Login.Response> call, Response<Login.Response> response) {
//                if(response.isSuccessful()) {
//                    Log.i("Login", "Success api request");
//
//                    final Map<String, List<String>> headers = response.headers().toMultimap();
//                    final int id = Integer.parseInt(headers.get("location").get(0));
//
//                    store.setLogin(true);
//                    store.setUserId(id);
//
//                    Intent intent = new Intent(loginActivity, TodoActivity.class);
//                    startActivity(intent);
//                } else Snackbar.make(view, "오류 입니다.", Snackbar.LENGTH_LONG).show();
//
//                loading_layout.setVisibility(View.INVISIBLE);
//            }
//
//            @Override
//            public void onFailure(Call<Login.Response> call, Throwable t) {
//                loading_layout.setVisibility(View.INVISIBLE);
//                Log.i("Login", String.format("error %s", t.toString()));
//                Snackbar.make(view, "오류 입니다.", Snackbar.LENGTH_LONG).show();
//            }
//        });

    }
}