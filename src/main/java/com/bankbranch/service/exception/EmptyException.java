package com.bankbranch.service.exception;

public class EmptyException extends RuntimeException{

    public EmptyException(String msg){
        super(msg);
    }

}