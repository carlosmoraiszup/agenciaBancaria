package com.bankbranch.service.exception;

public class LengthCpfException extends RuntimeException{

    public LengthCpfException(String msg){
        super(msg);
    }

    public LengthCpfException(String msg, Throwable cause){
        super(msg, cause);
    }
}