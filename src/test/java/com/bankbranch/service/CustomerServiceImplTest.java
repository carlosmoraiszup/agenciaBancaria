package com.bankbranch.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.bankbranch.AbstractTest;
import com.bankbranch.domain.Account;
import com.bankbranch.domain.Customer;
import com.bankbranch.dto.CustomerDTO;
import com.bankbranch.repository.AccountRepository;
import com.bankbranch.repository.CustomerRepository;
import com.bankbranch.service.exception.EmptyException;
import com.bankbranch.service.exception.ExistingCustomerException;
import com.bankbranch.service.exception.ObjectNotFoundException;
import com.bankbranch.service.impl.CustomerServiceImpl;


public class CustomerServiceImplTest extends AbstractTest {


    private static Validator validator;
    @InjectMocks
    private CustomerServiceImpl customerService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountService accountService;
    private Account account;
    private Customer customer;
    private CustomerDTO customerDTO;
    private List<Customer> list;

    @Before
    public void setUP() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        account = new Account(1, "01/01/2018", 0.0);

        customer = new Customer(1, "Carlos", "10575823607", account, "01/01/2018", "12");
        customerDTO = new CustomerDTO(customer);

        list = new ArrayList<>();
        list.add(customer);
    }


    //registerCustomer
    @Test
    public void registerCustomerOK() {
        when(customerRepository.findByCpf(anyString())).thenReturn(null);
        when(accountService.registerAccount()).thenReturn(account);
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);

        CustomerDTO customerDTO = customerService.registerCustomer(customer);
        assertNotNull(customerDTO);
        assertEquals(customerDTO.getNameCustomer(), "Carlos");
    }

    @Test
    public void registerCustomerAlreadyRegistered() {
        when(customerRepository.findByCpf(anyString())).thenReturn(customer);
        exception.expect(ExistingCustomerException.class);
        customerService.registerCustomer(customer);
    }

    @Test
    public void registerCustomerAccountNullException() {
        when(accountService.registerAccount()).thenReturn(null);
        exception.expect(EmptyException.class);
        customerService.registerCustomer(customer);
    }


    @Test
    public void CPFisNull() {
        Customer customerTest = new Customer(1, "Carlos", null, account, "01/01/2018", "12");
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customerTest);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void CPFerrorLength() {
        Customer customerTest = new Customer(1, "Carlos", "1023", account, "01/01/2018", "12");
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customerTest);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void CPFisEmpty() {
        Customer customerTest = new Customer(1, "Carlos", "", account, "01/01/2018", "12");
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customerTest);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void CPFinvalid() {
        Customer customerTest = new Customer(1, "Carlos", "11122233355", account, "01/01/2018", "12");
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customerTest);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void NameisNull() {
        Customer customerTest = new Customer(1, null, "105758236", account, "01/01/2018", "12");
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customerTest);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void NameerrorLength() {
        Customer customerTest = new Customer(1, "Ca", "10575823607", account, "01/01/2018", "12");
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customerTest);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void NameisEmpty() {
        Customer customerTest = new Customer(1, "", "10575823607", account, "01/01/2018", "12");
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customerTest);
        assertFalse(violations.isEmpty());
    }


    //findAllCustomers
    @Test
    public void findAllTestOK() {
        when(customerRepository.findAll()).thenReturn(list);

        List<CustomerDTO> returnMethod = customerService.findAllCustomer();
        assertNotNull(returnMethod);
        assertEquals(returnMethod.get(0).getNameCustomer(), "Carlos");
    }

    @Test
    public void findAllisEmpty() {
        list.clear();
        when(customerRepository.findAll()).thenReturn(list);

        List<CustomerDTO> returnMethod = customerService.findAllCustomer();
        assertTrue(returnMethod.isEmpty());
    }

    //findIdCustomer
    @Test
    public void findCustomerTestOK() {
        when(customerRepository.findByCpf(anyString())).thenReturn(customer);
        CustomerDTO c = customerService.findCpfCustomer(customer.getCpf());
        assertNotNull(c);
    }

    @Test
    public void findCustomerTestException() {
        when(customerRepository.findByCpf(anyString())).thenReturn(null);
        exception.expect(ObjectNotFoundException.class);
        customerService.findCpfCustomer("10575823607");
    }


    //DeleteCustomer
    @Test
    public void deleteCustomerTestOK() {
        when(customerRepository.findByCpf(anyString())).thenReturn((customer));
        customerService.deleteCustomer("10575823607");
        verify((customerRepository), times(1)).deleteById(any());
        verify((accountRepository), times(1)).deleteById(any());
    }

    @Test
    public void deleteCustomerTestException() {
        when(customerRepository.findByCpf(anyString())).thenReturn(customer);
        exception.expect(ObjectNotFoundException.class);
        customerService.deleteCustomer(null);
    }


    //UpdateCustomer
    @Test
    public void updateCustomerTestOK() {
        when(customerRepository.findByCpf(anyString())).thenReturn(customer);
        when(customerRepository.saveAndFlush(any())).thenReturn(customer);
        CustomerDTO c = customerService.updateCustomer(customer);
        assertNotNull(c);
    }


    @Test
    public void updateCustomerTestException() {
        when(customerRepository.findByCpf(anyString())).thenReturn(null);
        exception.expect(ObjectNotFoundException.class);
        customerService.updateCustomer(customer);
    }

}

