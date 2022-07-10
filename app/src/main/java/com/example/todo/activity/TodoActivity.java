package com.example.todo.activity;

import static com.example.todo.util.Service.service;
import static com.example.todo.util.Util.createDialog;
import static com.example.todo.util.Util.dismissAlert;
import static com.example.todo.util.Util.getCompletedTask;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.dto.Todo;
import com.example.todo.util.EmptyCallback;
import com.example.todo.util.ItemAdapter;
import com.example.todo.util.Store;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

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
    private AlertDialog dialog;
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
        itemAdapter = new ItemAdapter(todos) {
            @Override
            protected void deleteTodo(Integer id) {
                service.deleteTodo(id).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) { setHeaderMessage(); }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) { }
                });
            }

            @Override
            protected void toggleTodo(Integer id, Todo.Request todo) {
                service.updateTodo(id, todo).enqueue(new Callback<Todo.Response>() {
                    @Override
                    public void onResponse(Call<Todo.Response> call, Response<Todo.Response> response) { setHeaderMessage();}
                    @Override
                    public void onFailure(Call<Todo.Response> call, Throwable t) { }
                });
            }
        };
        recyclerView.setAdapter(itemAdapter);

        store = Store.getInstance();
        loading_layout.setVisibility(View.VISIBLE);

        dialog = getDialog();
        date_guide_text.setText(getNowFormattedDate());

        logout_btn.setOnClickListener(this::logout);
        add_btn.setOnClickListener(view -> dialog.show());

        store.setNowContext(TodoActivity.this);
        requestTodos();
    }

    private AlertDialog getDialog() {
        AlertDialog.Builder builder = createDialog(TodoActivity.this);

        TextInputLayout textContainer = new TextInputLayout(this);
        EditText editText = new EditText(this);

        textContainer.setPadding(60, 30, 60, 0);
        textContainer.setHint("task name");
        textContainer.addView(editText);

        builder
                .setTitle("새로운 TASK")
                .setView(textContainer)
                .setPositiveButton("생성", (dialogInterface, i) -> {
                    Todo.Request newTodo = new Todo.Request();
                    newTodo.setCompleted(false);
                    newTodo.setItem(editText.getText().toString());

                    editText.setText("");
                    service.createTodo(store.getUserId(), newTodo).enqueue(new Callback<Todo.Response>() {
                        @Override
                        public void onResponse(Call<Todo.Response> call, Response<Todo.Response> response) { requestTodos(); }

                        @Override
                        public void onFailure(Call<Todo.Response> call, Throwable t) { }
                    });
                })
                .setNegativeButton("취소", (dialogInterface, i) -> dialogInterface.cancel());

        return builder.create();
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
            .enqueue(new Callback<ArrayList<Todo.Response>>() {
                @Override
                public void onResponse(Call<ArrayList<Todo.Response>> call, Response<ArrayList<Todo.Response>> response) {
                    loading_layout.setVisibility(View.INVISIBLE);
                    if (!response.isSuccessful()) {
                        return;
                    }
                    ArrayList<Todo.Response> res_todos = response.body();
                    todos.clear();
                    todos.addAll(res_todos);

                    itemAdapter.notifyDataSetChanged();

                    greet_text.setText(String.format("Hello, %s", store.getUserName()));
                    setHeaderMessage();

                    recyclerView.scrollToPosition(0);
                }

                @Override
                public void onFailure(Call<ArrayList<Todo.Response>> call, Throwable t) {
                    Log.i("Todo", t.toString());
                }
            });
    }

    private void setHeaderMessage() {
        required_guide_text.setText(String.format("you complete %d/%d tasks!", getCompletedTask(todos), todos.size()));
        task_text.setText(String.format("today must finish 0 task"));
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

        dismissAlert();
    }
}