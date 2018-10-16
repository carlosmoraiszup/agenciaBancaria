package com.bankbranch.controller.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bankbranch.service.exception.DataIntegrityViolation;
import com.bankbranch.service.exception.EmptyException;
import com.bankbranch.service.exception.EqualAccountTransfer;
import com.bankbranch.service.exception.ExistingAccountException;
import com.bankbranch.service.exception.ExistingCustomerException;
import com.bankbranch.service.exception.InvalidAtributeException;
import com.bankbranch.service.exception.ObjectNotFoundException;
import com.bankbranch.service.exception.UnprocessableEntityException;

@ControllerAdvice
public class ResourceExceptionHandler {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(e.getMessage(),
                LocalDateTime.now().format(formatter));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(EqualAccountTransfer.class)
    public ResponseEntity<StandardError> equalAccountTransfer(EqualAccountTransfer e, HttpServletRequest request) {
        StandardError err = new StandardError(e.getMessage(),
                LocalDateTime.now().format(formatter));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<StandardError> unprocessableEntity(UnprocessableEntityException e,
            HttpServletRequest request) {
        StandardError err = new StandardError(e.getMessage(),
                LocalDateTime.now().format(formatter));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

    @ExceptionHandler(ExistingAccountException.class)
    public ResponseEntity<StandardError> existingAccount(ExistingAccountException e, HttpServletRequest request) {
        StandardError err = new StandardError(e.getMessage(),
                LocalDateTime.now().format(formatter));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(ExistingCustomerException.class)
    public ResponseEntity<StandardError> existingCustomer(ExistingCustomerException e, HttpServletRequest request) {
        StandardError err = new StandardError(e.getMessage(),
                LocalDateTime.now().format(formatter));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }


    @ExceptionHandler(EmptyException.class)
    public ResponseEntity<StandardError> emptyException(EmptyException e, HttpServletRequest request) {
        StandardError err = new StandardError(e.getMessage(),
                LocalDateTime.now().format(formatter));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(InvalidAtributeException.class)
    public ResponseEntity<StandardError> invalidAtributeException(InvalidAtributeException e,
            HttpServletRequest request) {
        StandardError err = new StandardError(e.getMessage(),
                LocalDateTime.now().format(formatter));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<StandardError> dataIntegrityException(DataIntegrityViolation e,
//            HttpServletRequest request) {
//        StandardError err = new StandardError(e.getMessage(),
//                LocalDateTime.now().format(formatter));
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError err = new ValidationError("Erro de validação",
                LocalDateTime.now().format(formatter));
        for (FieldError x : e.getBindingResult().getFieldErrors()) {
            err.addErrors(x.getField(), x.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }


}