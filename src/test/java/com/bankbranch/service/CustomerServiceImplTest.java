package com.bankbranch.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bankbranch.AbstractTest;
import com.bankbranch.domain.Account;
import com.bankbranch.domain.Customer;
import com.bankbranch.domain.enums.Profile;
import com.bankbranch.dto.CustomerDTO;
import com.bankbranch.repository.AccountRepository;
import com.bankbranch.repository.CustomerRepository;
import com.bankbranch.security.UserSS;
import com.bankbranch.service.exception.AuthorizationException;
import com.bankbranch.service.exception.EmptyException;
import com.bankbranch.service.exception.ExistingCustomerException;
import com.bankbranch.service.exception.ObjectNotFoundException;
import com.bankbranch.service.impl.CustomerServiceImpl;
import com.bankbranch.service.impl.UserServiceImpl;

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

    @Mock
    private UserSS userSS;

    @Mock
    private UserServiceImpl userServiceImpl;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
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


    //register
    @Test
    public void register_NewUserNotYetRegisteredWithTheBankReturnOK() {
        when(customerRepository.findByCpf("Julio")).thenReturn(null);
        when(accountService.registerAccount()).thenReturn(account);
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);

        CustomerDTO customerDTO = customerService.registerCustomer(customer);
        assertNotNull(customerDTO);
        assertEquals(customerDTO.getNameCustomer(), "Carlos");
    }

    @Test
    public void register_RegisterNewUserAlreadyRegisteredInTheBankReturnThrowException() {
        when(customerRepository.findByCpf(anyString())).thenReturn(customer);
        exception.expect(ExistingCustomerException.class);
        customerService.registerCustomer(customer);
    }

    @Test
    public void register_WhenTheAccountIsNullReturnThrowException() {
        when(accountService.registerAccount()).thenReturn(null);
        exception.expect(EmptyException.class);
        customerService.registerCustomer(customer);
    }


    @Test
    public void constraintCPF_WhenCPFisNullReturnConstraintViolation() {
        Customer customerTest = new Customer(1, "Carlos", null, account, "01/01/2018", "12");
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customerTest);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void constraintCPF_WhenCPFErrorLengthReturnConstraintViolation() {
        Customer customerTest = new Customer(1, "Carlos", "1023", account, "01/01/2018", "12");
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customerTest);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void constraintCPF_WhenCPFIsEmptyReturnConstraintViolation() {
        Customer customerTest = new Customer(1, "Carlos", "", account, "01/01/2018", "12");
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customerTest);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void constraintCPF_WhenCPFIsInvalidReturnConstraintViolation() {
        Customer customerTest = new Customer(1, "Carlos", "11122233355", account, "01/01/2018", "12");
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customerTest);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void constraintName_WhenNameisNullReturnConstraintViolation() {
        Customer customerTest = new Customer(1, null, "105758236", account, "01/01/2018", "12");
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customerTest);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void constraintName_WhenNameErrorLengthReturnConstraintViolation() {
        Customer customerTest = new Customer(1, "Ca", "10575823607", account, "01/01/2018", "12");
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customerTest);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void constraintName_WhenNameIsEmptyReturnConstraintViolation() {
        Customer customerTest = new Customer(1, "", "10575823607", account, "01/01/2018", "12");
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customerTest);
        assertFalse(violations.isEmpty());
    }


    //findAll
    @Test
    public void findAll_WhenThereIsARegisteredCustomerReturnsListWithCustomers() {
        when(customerRepository.findAll()).thenReturn(list);

        List<CustomerDTO> returnMethod = customerService.findAllCustomer();
        assertNotNull(returnMethod);
        assertEquals(returnMethod.get(0).getNameCustomer(), "Carlos");
    }

    @Test
    public void findAll_WhenThereAreNoRegisteredCustomerReturnEmptyList() {
        list.clear();
        when(customerRepository.findAll()).thenReturn(list);

        List<CustomerDTO> returnMethod = customerService.findAllCustomer();
        assertTrue(returnMethod.isEmpty());
    }

    //findIdCustomer
    @Test
    public void viewProfileData_IfUserIsAuthorizedReturnsVourInformation() {
        Set<Profile> profiles = new HashSet<>();
        profiles.add(Profile.CUSTOMER);
        UserSS user = new UserSS(null, "10575823607", null, profiles);
        when(userServiceImpl.authenticated()).thenReturn(user);
        when(customerRepository.findByCpf(user.getUsername())).thenReturn(customer);
        CustomerDTO c = customerService.viewProfileData();
        assertNotNull(c);
    }

    @Test
    public void viewProfileData_IfUserIsNullReturnsThrowException() {
        when(userServiceImpl.authenticated()).thenReturn(null);
        exception.expect(ObjectNotFoundException.class);
        exception.expectMessage("User not found!");
        customerService.viewProfileData();
    }

    @Test
    public void viewProfileData_IfCustomerIsNullReturnsThrowException() {
        Set<Profile> profiles = new HashSet<>();
        profiles.add(Profile.CUSTOMER);
        UserSS user = new UserSS(null, "10575823607", null, profiles);
        when(userServiceImpl.authenticated()).thenReturn(user);
        when(customerRepository.findByCpf("10575823607")).thenReturn(null);
        exception.expect(ObjectNotFoundException.class);
        exception.expectMessage("Customer not found!");
        customerService.viewProfileData();
    }

    @Test
    public void viewProfileData_WhenTheProfileDiffersFromAdminAndCpfFromTheClientOtherThanUsernameThrowException() {
        Set<Profile> profiles = new HashSet<>();
        profiles.add(Profile.CUSTOMER);
        UserSS user = new UserSS(null, "96300388034", null, profiles);
        when(userServiceImpl.authenticated()).thenReturn(user);
        when(customerRepository.findByCpf(user.getUsername())).thenReturn(customer);
        exception.expect(AuthorizationException.class);
        exception.expectMessage("Access denied!");
        customerService.viewProfileData();
    }




    //DeleteCustomer
    @Test
    public void deleteCustomer_WhenProfileIsAdminDeleteAccountAndCustomerPassedByEndpoint() {
        when(customerRepository.findByCpf(anyString())).thenReturn((customer));
        customerService.deleteCustomer("10575823607");
        verify((customerRepository), times(1)).deleteById(any());
        verify((accountRepository), times(1)).deleteById(any());
    }

    @Test
    public void deleteCustomer_WhenProfileIsAdminAndCanNotFindClientPassedByTheEndpointThrowException() {
        when(customerRepository.findByCpf(anyString())).thenReturn(customer);
        exception.expect(ObjectNotFoundException.class);
        customerService.deleteCustomer(null);
    }


    //UpdateCustomer
    @Test
    public void updateCustomer_WhenProfileIsAdminUpdateCustomerStepByEndpoint() {
        when(customerRepository.findByCpf(anyString())).thenReturn(customer);
        when(customerRepository.saveAndFlush(any())).thenReturn(customer);
        CustomerDTO c = customerService.updateCustomer(customer);
        assertNotNull(c);
    }


    @Test
    public void updateCustomer_WhenProfileIsAdminAndICanNotFindTheCustomerPassedByEndpointThrowException() {
        when(customerRepository.findByCpf(anyString())).thenReturn(null);
        exception.expect(ObjectNotFoundException.class);
        customerService.updateCustomer(customer);
    }

}

