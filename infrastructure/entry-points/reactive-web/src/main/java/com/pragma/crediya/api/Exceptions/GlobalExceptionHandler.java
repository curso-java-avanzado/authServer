package com.pragma.crediya.api.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleValidationException(
            WebExchangeBindException ex) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String message = error.getDefaultMessage();
            Object rejectedValue = error.getRejectedValue();
            
            // Crear mensaje con el valor rechazado
            String fullMessage = String.format("%s. Valor recibido: '%s'", message, rejectedValue);
            errors.put(field, fullMessage);
            
            // Log detallado
            log.warn("Campo '{}': {} | Valor rechazado: '{}' | Código de error: '{}'", 
                    field, message, rejectedValue, error.getCode());
        });

        ErrorResponse errorResponse = new ErrorResponse("Error de validación", errors);
        
        return Mono.just(ResponseEntity.badRequest().body(errorResponse));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleIllegalArgumentException(
            IllegalArgumentException ex) {

        log.warn("IllegalArgumentException: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Error de validación", Map.of("error", ex.getMessage()));
        return Mono.just(ResponseEntity.badRequest().body(errorResponse));
    }

    @ExceptionHandler(UserCreatedException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleUserCreatedException(
            UserCreatedException ex) {

        log.warn("UserCreatedException: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Error de creación de usuario", Map.of("error", ex.getMessage()));
        return Mono.just(ResponseEntity.badRequest().body(errorResponse));
    }

    @Getter
    @Setter
    public static class ErrorResponse {
        private String message;
        private Map<String, String> errors;

        public ErrorResponse(String message, Map<String, String> errors) {
            this.message = message;
            this.errors = errors;
        }
    }
}
