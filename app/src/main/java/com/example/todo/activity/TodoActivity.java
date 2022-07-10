package com.example.todo.activity;

import static com.example.todo.util.Service.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.dto.Todo;
import com.example.todo.util.ItemAdapter;
import com.example.todo.util.Store;
import com.example.todo.util.Util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoActivity extends AppCompatActivity {
    private TextView greet_text, task_text, required_guide_text, date_guide_text;
    private ImageButton logout_btn, filter_btn, add_btn;
    private ArrayList<Todo.Response> todos;
    private LinearLayout loading_layout;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);

        required_guide_text = findViewById(R.id.required_guide_text);
        loading_layout = findViewById(R.id.todo_loading_layout);
        date_guide_text = findViewById(R.id.date_guide_text);
        recyclerView = findViewById(R.id.list_view);
        greet_text = findViewById(R.id.greet_text);
        logout_btn = findViewById(R.id.logout_btn);
        filter_btn = findViewById(R.id.filter_btn);
        task_text = findViewById(R.id.task_text);
        add_btn = findViewById(R.id.add_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        todos = new ArrayList<>();
        itemAdapter = new ItemAdapter(todos);
        recyclerView.setAdapter(itemAdapter);

        store = Store.getInstance();
        loading_layout.setVisibility(View.VISIBLE);
        logout_btn.setOnClickListener(this::logout);

        date_guide_text.setText(getNowFormattedDate());

        store.setNowContext(TodoActivity.this);
        requestTodos();
    }

    private void logout(View view) {
        Store.initStore();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        deleteFile("secret_file");
    }

    private void requestTodos() {
        service
            .getTodos(store.getUserId())
            .enqueue(new Callback<List<Todo.Response>>() {
                @Override
                public void onResponse(Call<List<Todo.Response>> call, Response<List<Todo.Response>> response) {
                    loading_layout.setVisibility(View.INVISIBLE);
                    if (!response.isSuccessful()) {
                        return;
                    }
                    List<Todo.Response> res_todos = response.body();
                    todos.addAll(res_todos);
                    itemAdapter.notifyDataSetChanged();

                    greet_text.setText(String.format("Hello, %s", store.getUserName()));
                    required_guide_text.setText(String.format("you complete %d/%d tasks!", Util.getCompletedTask(todos), todos.size()));
                    task_text.setText(String.format("today must finish 0 task"));
                }

                @Override
                public void onFailure(Call<List<Todo.Response>> call, Throwable t) {
                    Log.i("Todo", t.toString());
                }
            });
    }

    private String getNowFormattedDate() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");         // 포맷 적용
            return now.format(formatter);
        } else {
            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            return formatter.format(now);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        Util.dismissAlert();
    }
}