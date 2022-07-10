package com.example.todo.util;

import android.content.Context;

import com.example.todo.dto.Todo;

import java.util.ArrayList;

public class Store {
    private boolean isLogin = false;
    private Integer userId;
    private String userName;
    private ArrayList<Todo.Response> todos;
    private Context nowContext = null;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Todo.Response> getTodos() {
        return todos;
    }

    public void setTodos(ArrayList<Todo.Response> todos) {
        this.todos = todos;
    }

    public static void setInstance(Store instance) {
        Store.instance = instance;
    }

    public Context getNowContext() {
        return nowContext;
    }

    public void setNowContext(Context nowContext) {
        this.nowContext = nowContext;
    }

    private static Store instance = new Store();
    public static Store getInstance() { return instance; }

    public static void initStore() {
        final Store instance = getInstance();
        instance.isLogin = false;
        instance.userId = null;
        instance.userName = null;
        instance.todos = null;
        instance.nowContext = null;
    }
}
