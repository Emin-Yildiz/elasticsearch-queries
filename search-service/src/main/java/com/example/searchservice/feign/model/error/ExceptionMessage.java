package com.example.searchservice.feign.model.error;

public record ExceptionMessage(String timestamp,
                               int status,
                               String error,
                               String message,
                               String path) {
}
