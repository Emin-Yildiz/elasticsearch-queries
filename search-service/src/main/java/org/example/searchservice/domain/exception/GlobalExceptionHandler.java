package org.example.searchservice.domain.exception;

import org.example.searchservice.domain.exception.exception.NotAvailableException;
import org.example.searchservice.domain.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotAvailableException.class) // Bulunamadı : 404
    public ResponseEntity<Object> handel(NotAvailableException exception) {
        ErrorResponse<Object> response = new ErrorResponse<>(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
