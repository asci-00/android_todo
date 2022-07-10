package com.example.todo.dto;

public class Todo {
    public static class Request { }
    public static class Response {
        private Integer id;
        private String item;
        private Boolean completed;
        private Long timestamp;
        private Long deadline;

        public void setId(Integer id) {
            this.id = id;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public void setCompleted(Boolean completed) {
            this.completed = completed;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }

        public void setDeadline(Long deadline) {
            this.deadline = deadline;
        }

        public Integer getId() {
            return id;
        }

        public String getItem() {
            return item;
        }

        public Boolean getCompleted() {
            return completed;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public Long getDeadline() {
            return deadline;
        }

        public Response(Integer id, String item, Boolean completed, Long timestamp, Long deadline) {
            this.id = id;
            this.item = item;
            this.completed = completed;
            this.timestamp = timestamp;
            this.deadline = deadline;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "id=" + id +
                    ", item='" + item + '\'' +
                    ", completed=" + completed +
                    ", timestamp=" + timestamp +
                    ", deadline=" + deadline +
                    '}';
        }
    }
}
