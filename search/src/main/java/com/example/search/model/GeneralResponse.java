package com.example.search.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Schema(description = "Standard response wrapper for all API responses")
public class GeneralResponse<T> {

    @Schema(description = "HTTP status code", example = "200")
    private int code;
    
    @Schema(description = "Timestamp of the response", example = "2025-05-01T10:30:45")
    private LocalDateTime timestamp;
    
    @Schema(description = "Response data")
    private T data;

    public GeneralResponse() {
        this.timestamp = LocalDateTime.now();
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}