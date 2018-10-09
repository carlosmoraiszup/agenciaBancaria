package com.bankbranch.service.exception;

public class InvalidAtributeException extends RuntimeException{

    public InvalidAtributeException(String msg){
        super(msg);
    }

    public InvalidAtributeException(String msg, Throwable cause){
        super(msg, cause);
    }


}
