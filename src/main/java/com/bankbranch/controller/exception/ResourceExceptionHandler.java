package com.bankbranch.controller.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bankbranch.service.exception.EmptyException;
import com.bankbranch.service.exception.EqualAccountTransfer;
import com.bankbranch.service.exception.ExistingAccountException;
import com.bankbranch.service.exception.InvalidAtributeException;
import com.bankbranch.service.exception.LengthCpfException;
import com.bankbranch.service.exception.ObjectNotFoundException;
import com.bankbranch.service.exception.UnprocessableEntityException;

@ControllerAdvice
public class ResourceExceptionHandler {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(),
                LocalDateTime.now().format(formatter));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(EqualAccountTransfer.class)
    public ResponseEntity<StandardError> equalAccountTransfer(EqualAccountTransfer e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), e.getMessage(),
                LocalDateTime.now().format(formatter));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<StandardError> unprocessableEntity(UnprocessableEntityException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(),
                LocalDateTime.now().format(formatter));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

    @ExceptionHandler(ExistingAccountException.class)
    public ResponseEntity<StandardError> existingAccount(ExistingAccountException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.CONFLICT.value(), e.getMessage(),
                LocalDateTime.now().format(formatter));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(LengthCpfException.class)
    public ResponseEntity<StandardError> lengthCpf(LengthCpfException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.LENGTH_REQUIRED.value(), e.getMessage(),
                LocalDateTime.now().format(formatter));
        return ResponseEntity.status(HttpStatus.LENGTH_REQUIRED).body(err);
    }

   @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> exception(Exception e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), "Attribute content declared invalid ",
                LocalDateTime.now().format(formatter));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(EmptyException.class)
    public ResponseEntity<StandardError> emptyException(EmptyException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                LocalDateTime.now().format(formatter));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(InvalidAtributeException.class)
    public ResponseEntity<StandardError> invalidAtributeException(InvalidAtributeException e,
            HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                LocalDateTime.now().format(formatter));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }


}