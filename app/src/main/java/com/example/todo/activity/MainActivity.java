package com.example.todo.activity;

import static com.example.todo.util.Service.service;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.R;
import com.example.todo.dto.Login;
import com.example.todo.util.Store;
import com.example.todo.util.Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Button login_btn;
    private Intent intent;
    private FileInputStream file;
    private Store store;
    private Integer id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_btn = findViewById(R.id.login_btn);
        store = Store.getInstance();

        try {
            file = openFileInput("secret_file");
            id = Integer.parseInt(Util.readLocalStorage(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // TODO: 로그인 데이터 존재 여부 확인 및 이동

        if(id != null) {
            intent = new Intent(this, TodoActivity.class);
            store.setUserId(id);

            service.getUser(id).enqueue(new Callback<Login.Response>() {
                @Override
                public void onResponse(Call<Login.Response> call, Response<Login.Response> response) {
                    if(!response.isSuccessful()) return;
                    store.setUserName(response.body().getName());
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Login.Response> call, Throwable t) {

                }
            });
        }

        login_btn.setOnClickListener(view -> {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        Util.dismissAlert();
    }
}