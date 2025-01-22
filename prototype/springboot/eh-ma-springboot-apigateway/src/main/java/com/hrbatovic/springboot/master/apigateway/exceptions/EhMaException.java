package com.hrbatovic.springboot.master.apigateway.exceptions;

public class EhMaException extends RuntimeException {
    private final int code;

    public EhMaException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
