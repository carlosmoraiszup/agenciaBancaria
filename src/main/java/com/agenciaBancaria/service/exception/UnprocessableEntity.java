package com.agenciaBancaria.service.exception;

public class UnprocessableEntity extends RuntimeException{

    public UnprocessableEntity(String msg){
        super(msg);
    }

    public UnprocessableEntity(String msg, Throwable cause){
        super(msg, cause);
    }
}