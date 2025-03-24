package com.example.kotlinpojo.domain.response;

public record ErrorResponse<T>(Boolean success, T message) {
    public ErrorResponse(T message) {
        this(Boolean.FALSE,message);
    }
}
