package com.example.kotlinpojo.domain.exception;

import com.example.kotlinpojo.domain.exception.exceptions.AlreadyAvailableException;
import com.example.kotlinpojo.domain.exception.exceptions.BadRequestException;
import com.example.kotlinpojo.domain.exception.exceptions.NotAvailableException;
import com.example.kotlinpojo.domain.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotAvailableException.class) // Bulunamadı : 404
    public ResponseEntity<Object> handel(NotAvailableException exception) {
        ErrorResponse<Object> response = new ErrorResponse<>(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AlreadyAvailableException.class) // Mevcut : 409
    public ResponseEntity<Object> handel(AlreadyAvailableException exception) {
        ErrorResponse<Object> response = new ErrorResponse<>(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(BadRequestException.class) // 400
    public ResponseEntity<Object> handel(BadRequestException exception) {
        ErrorResponse<Object> response = new ErrorResponse<>(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        ErrorResponse<Object> response = new ErrorResponse<>(null, getErrorsMap(errors));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
