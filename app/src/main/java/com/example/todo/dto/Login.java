package com.example.todo.dto;

public class Login {
    public static class Response { }
    public static class Request {
        private String name;
        private String pw;

        public Request(String name, String pw) {
            this.name = name;
            this.pw = pw;
        }
    }
}
