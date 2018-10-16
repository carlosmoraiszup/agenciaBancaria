package com.bankbranch.service.exception;

public class DataIntegrityViolation extends RuntimeException{

    public DataIntegrityViolation(String msg){
        super(msg);
    }

}