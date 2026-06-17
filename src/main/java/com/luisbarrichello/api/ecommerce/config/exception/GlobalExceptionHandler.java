package com.luisbarrichello.api.ecommerce.config.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(String message) {}
    public record ValidationErrorResponse(String field, String message) {
        public ValidationErrorResponse(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorResponse>> handleError400(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors().stream()
                .map(ValidationErrorResponse::new)
                .toList();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleError400(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleErrorBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleErrorAuthentication() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleErrorAccessDenied() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleErrors(ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleError500(Exception ex) {
        log.error("Unexpected error", ex);
        return ResponseEntity.internalServerError().body(new ErrorResponse("An unexpected error occurred"));
    }
}