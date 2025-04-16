package com.example.searchservice.domain.exception;

import com.example.searchservice.domain.exception.exception.NotAvailableException;
import com.example.searchservice.domain.exception.exception.ServiceUnavailableException;
import com.example.searchservice.domain.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(NotAvailableException.class) // Bulunamadı : 404
    public ResponseEntity<Object> handel(NotAvailableException exception) {
        ErrorResponse<Object> response = new ErrorResponse<>(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<Object> handel(ServiceUnavailableException exception) {
        ErrorResponse<Object> response = new ErrorResponse<>(exception.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

}
