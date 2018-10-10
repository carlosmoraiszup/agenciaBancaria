package com.bankbranch.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import com.bankbranch.AbstractTest;
import com.bankbranch.domain.Customer;
import com.bankbranch.service.exception.EmptyException;
import com.bankbranch.service.exception.InvalidAtributeException;
import com.bankbranch.service.exception.LengthCpfException;
import com.bankbranch.service.impl.ValidationsImpl;


public class ValidationsImplImplTest extends AbstractTest {


    @InjectMocks
    private ValidationsImpl validationsImpl;

    private Customer customer;

    @Before
    public void setUP() {

    }

    //validationCpf
    @Test
    public void validationCpfOK() {
        customer = new Customer(1, "Carlos", "10575823607", null, "01/01/2018");
        validationsImpl.validationCpf(customer.getCpf());
    }

    @Test
    public void validationCpfException() {
        customer = new Customer(1, "Carlos", "11111111111", null, "01/01/2018");
        exception.expect(InvalidAtributeException.class);
        validationsImpl.validationCpf(customer.getCpf());
    }

    @Test
    public void validationCpfNullException() {
        customer = new Customer(1, "Carlos", null, null, "01/01/2018");
        exception.expect(LengthCpfException.class);
        validationsImpl.validationCpf(customer.getCpf());
    }


    //validationName
    @Test
    public void validationNamefOK() {
        customer = new Customer(1, "Carlos", "10575823607", null, "01/01/2018");
        validationsImpl.validationName(customer.getNameCustomer());
    }

    @Test
    public void validationCpfExceptionNull() {
        customer = new Customer(1, null, "10575823607", null, "01/01/2018");
        exception.expect(EmptyException.class);
        validationsImpl.validationName(customer.getNameCustomer());
    }

    @Test
    public void validationCpfExceptionEmpty() {
        customer = new Customer(1, "", "10575823607", null, "01/01/2018");
        exception.expect(EmptyException.class);
        validationsImpl.validationName(customer.getNameCustomer());
    }

    //cleanCpf
    @Test
    public void cleanCpfOK() {
        customer = new Customer(1, "Carlos", "105.758.236-07", null, "01/01/2018");
        validationsImpl.cleanCPF(customer.getCpf());
    }

    @Test
    public void cleanCpfException() {
        customer = new Customer(1, "Carlos", "105.758.236-07-", null, "01/01/2018");
        exception.expect(LengthCpfException.class);
        validationsImpl.cleanCPF(customer.getCpf());
    }

    //cleanCpf
    @Test
    public void simpleCheckNotValidOKOne() {
        customer = new Customer(1, "Carlos", "105.758.236-0", null, "01/01/2018");
        validationsImpl.simpleCheckNotValid(customer.getCpf());
    }

    @Test
    public void simpleCheckNotValidOKTwo() {
        customer = new Customer(1, "Carlos", "00000000000", null, "01/01/2018");
        validationsImpl.simpleCheckNotValid(customer.getCpf());
    }

    //cleanCpf
    @Test
    public void isValidOK() {
        customer = new Customer(1, "Carlos", "105.758.236-07", null, "01/01/2018");
        validationsImpl.isValidCPF(customer.getCpf());
    }

    @Test
    public void isValidExceptionNull() {
        customer = new Customer(1, "Carlos", null, null, "01/01/2018");
        exception.expect(LengthCpfException.class);
        validationsImpl.isValidCPF(customer.getCpf());
    }

    @Test
    public void isValidExceptionLengthInvalid() {
        customer = new Customer(1, "Carlos", "105758236077", null, "01/01/2018");
        exception.expect(LengthCpfException.class);
        validationsImpl.isValidCPF(customer.getCpf());
    }

}

