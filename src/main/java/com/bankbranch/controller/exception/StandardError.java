package com.bankbranch.controller.exception;

import java.io.Serializable;

public class StandardError implements Serializable {
    private String msg;

    private String dateStamp;


    public StandardError(String msg, String timeStamp) {
        this.msg = msg;
        this.dateStamp = timeStamp;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTimeStamp() {
        return dateStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.dateStamp = timeStamp;
    }
}
