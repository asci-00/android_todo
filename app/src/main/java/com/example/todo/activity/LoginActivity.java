package com.example.todo.activity;

import static com.example.todo.util.Api.service;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.R;
import com.example.todo.dto.Login;
import com.example.todo.util.Store;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout id_text, pw_text;
    LinearLayout loading_layout;
    ImageButton back_btn;
    Button login_btn;
    Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loading_layout = findViewById(R.id.loading_layout);
        login_btn = findViewById(R.id.login_req_btn);
        back_btn = findViewById(R.id.back_btn);
        id_text = findViewById(R.id.id_text);
        pw_text = findViewById(R.id.pw_text);
        store = Store.getInstance();

        loading_layout.setVisibility(View.INVISIBLE);

        back_btn.setOnClickListener(view -> super.onBackPressed());

        LoginActivity loginActivity = this;

        login_btn.setOnClickListener(view -> {
            loading_layout.setVisibility(View.VISIBLE);

            final String name = id_text.getEditText().toString();
            final String pw = pw_text.getEditText().toString();

            Login.Request request = new Login.Request(name, pw);

            service.login(request).enqueue(new Callback<Login.Response>() {
                @Override
                public void onResponse(Call<Login.Response> call, Response<Login.Response> response) {
                    if(response.isSuccessful()) {
                        Log.i("Login", "Success api request");

                        final Map<String, List<String>> headers = response.headers().toMultimap();
                        final int id = Integer.parseInt(headers.get("location").get(0));

                        store.setLogin(true);
                        store.setUserId(id);

                        Intent intent = new Intent(loginActivity, TodoActivity.class);
                        startActivity(intent);
                    } else Snackbar.make(view, "오류 입니다.", Snackbar.LENGTH_LONG).show();

                    loading_layout.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<Login.Response> call, Throwable t) {
                    loading_layout.setVisibility(View.INVISIBLE);
                    Log.i("Login", String.format("error %s", t.toString()));
                    Snackbar.make(view, "오류 입니다.", Snackbar.LENGTH_LONG).show();
                }
            });
        });
    }

    //화면 터치 시 키보드 내려감
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}