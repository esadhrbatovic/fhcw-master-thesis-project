package com.hrbatovic.springboot.master.product.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EhMaExceptionHandler {

    @ExceptionHandler(EhMaException.class)
    public ResponseEntity<Object> handleEhMaException(EhMaException ex) {
        return ResponseEntity
                .status(ex.getCode())
                .body(new ErrorResponse(ex.getCode(), ex.getMessage()));
    }

    public static class ErrorResponse {
        private final int code;
        private final String message;

        public ErrorResponse(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
