package com.hrbatovic.micronaut.master.cart.exceptions;

public class EhMaException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;

    public EhMaException(int errorCode, String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode(){
        return errorCode;
    }

    @Override
    public String getMessage(){
        return errorMessage;
    }
}
