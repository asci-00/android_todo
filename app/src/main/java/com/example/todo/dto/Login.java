package com.example.todo.dto;

import com.google.gson.annotations.SerializedName;

public class Login {
    public static class Response { }
    public static class Request {
        @SerializedName("name")
        private String name;
        @SerializedName("password")
        private String pw;

        public Request(String name, String pw) {
            this.name = name;
            this.pw = pw;
        }
    }
}
