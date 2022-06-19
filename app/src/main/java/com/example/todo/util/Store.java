package com.example.todo.util;

public class Store {
    private boolean isLogin = false;
    private Integer userId;

    private static Store instance = new Store();
    public static Store getInstance() { return instance; }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
