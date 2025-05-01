package com.example.search.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GeneralResponse<T> {
    private int code;
    private String timestamp;
    private T data;

    public GeneralResponse() {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public GeneralResponse(int code, T data) {
        this();
        this.code = code;
        this.data = data;
    }

    // Getters and Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}