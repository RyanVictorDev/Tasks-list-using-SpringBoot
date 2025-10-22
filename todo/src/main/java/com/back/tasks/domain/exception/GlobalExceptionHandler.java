package com.back.tasks.domain.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalValueException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalValueException(IllegalValueException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Invalid value");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());

        Throwable cause = ex.getMostSpecificCause();

        if (cause instanceof InvalidFormatException invalidEx && invalidEx.getTargetType().isEnum()) {
            Object invalidValue = invalidEx.getValue();
            Class<?> enumType = invalidEx.getTargetType();

            String[] allowedValues = Arrays.stream(enumType.getEnumConstants())
                    .map(Object::toString)
                    .toArray(String[]::new);

            body.put("error", "Invalid Enum Value");
            body.put("message", String.format(
                    "Invalid value '%s' for field '%s'. Allowed values are: %s.",
                    invalidValue,
                    invalidEx.getPath().isEmpty() ? "unknown" : invalidEx.getPath().getFirst().getFieldName(),
                    String.join(", ", allowedValues)
            ));
        } else {
            body.put("error", "Malformed JSON request");
            body.put("message", cause.getMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
