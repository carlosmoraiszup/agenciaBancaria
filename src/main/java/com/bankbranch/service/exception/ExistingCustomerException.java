package com.bankbranch.service.exception;

public class ExistingCustomerException extends RuntimeException {

    public ExistingCustomerException(String msg) {
        super(msg);
    }

}
