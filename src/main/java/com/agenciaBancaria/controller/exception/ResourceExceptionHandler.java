package com.agenciaBancaria.controller.exception;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.agenciaBancaria.service.exception.EqualAccountTransfer;
import com.agenciaBancaria.service.exception.ObjectNotFoundException;
import com.agenciaBancaria.service.exception.UnprocessableEntity;

@ControllerAdvice
public class ResourceExceptionHandler {
    private Timestamp data = new Timestamp(System.currentTimeMillis());
    private String date = new SimpleDateFormat("dd/MM/yyyy").format(data.getTime());

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), date);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(EqualAccountTransfer.class)
    public ResponseEntity<StandardError> objectNotFound(EqualAccountTransfer e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(), date);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(UnprocessableEntity.class)
    public ResponseEntity<StandardError> objectNotFound(UnprocessableEntity e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(), date);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

}