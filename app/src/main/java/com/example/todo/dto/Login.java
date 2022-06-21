package com.example.todo.dto;

import com.google.gson.annotations.SerializedName;

public class Login {
    public static class Response {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Response(String name) {
            this.name = name;
        }
    }
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
