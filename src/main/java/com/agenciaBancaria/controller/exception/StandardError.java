package com.agenciaBancaria.controller.exception;

import java.io.Serializable;

public class StandardError implements Serializable {
    private Integer status;
    private String msg;

    private String dateStamp;


    public StandardError(Integer status, String msg, String timeStamp) {
        this.status = status;
        this.msg = msg;
        this.dateStamp = timeStamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
