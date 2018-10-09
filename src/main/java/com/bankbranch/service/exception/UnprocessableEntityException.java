package com.bankbranch.service.exception;

public class UnprocessableEntityException extends RuntimeException{

    public UnprocessableEntityException(String msg){
        super(msg);
    }

    public UnprocessableEntityException(String msg, Throwable cause){
        super(msg, cause);
    }
}