package com.bankbranch.service.exception;

public class EmptyException extends RuntimeException{

    public EmptyException(String msg){
        super(msg);
    }

    public EmptyException(String msg, Throwable cause){
        super(msg, cause);
    }
}