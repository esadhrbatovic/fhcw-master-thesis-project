package com.hrbatovic.micronaut.master.payment.exceptions;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.inject.Singleton;

@Singleton
@Produces
public class EhMaExceptionHandler implements ExceptionHandler<EhMaException, HttpResponse<?>> {

    @Override
    public HttpResponse<?> handle(HttpRequest request, EhMaException exception) {
        return HttpResponse
                .status(exception.getErrorCode(), "")
                .body(new ErrorResponse(exception.getErrorCode(), exception.getMessage()));
    }

    @Serdeable
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