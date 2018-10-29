package com.bankbranch.service.exception;

public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException(String msg) {
        super(msg);
    }
}