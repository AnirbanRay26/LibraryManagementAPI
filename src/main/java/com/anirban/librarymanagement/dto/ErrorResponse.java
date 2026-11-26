package com.anirban.librarymanagement.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(LocalDateTime timestamp, int status, String error,
                            String message, Map<String, String> validationErrors) {
    public static ErrorResponse of(int status, String error, String message) {
        return new ErrorResponse(LocalDateTime.now(), status, error, message, null);
    }
    public static ErrorResponse validation(Map<String, String> errors) {
        return new ErrorResponse(LocalDateTime.now(), 400, "Bad Request", "Validation failed", errors);
    }
}
