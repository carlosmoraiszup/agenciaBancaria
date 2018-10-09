package com.bankbranch.service.exception;

public class ExistingAccountException extends RuntimeException{

    public ExistingAccountException(String msg){
        super(msg);
    }

    public ExistingAccountException(String msg, Throwable cause){
        super(msg, cause);
    }

}
