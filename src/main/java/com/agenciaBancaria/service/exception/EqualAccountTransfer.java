package com.agenciaBancaria.service.exception;

public class EqualAccountTransfer extends RuntimeException{

    public EqualAccountTransfer(String msg){
        super(msg);
    }

    public EqualAccountTransfer(String msg, Throwable cause){
        super(msg, cause);
    }
}