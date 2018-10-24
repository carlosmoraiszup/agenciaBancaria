package com.bankbranch.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bankbranch.AbstractTest;
import com.bankbranch.domain.Account;
import com.bankbranch.domain.Customer;
import com.bankbranch.repository.CustomerRepository;
import com.bankbranch.service.impl.UserDetailsServiceImpl;


public class UserDetailsServiceImplTest extends AbstractTest {


    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private CustomerRepository customerRepository;

    private Customer customer;
    private Account account;

    @Before
    public void setUP() {
        account = new Account(1, "01/01/2018", 4.0);
        customer = new Customer(1, "Carlos", "10575823607", account, "01/01/2018", "12");
    }

    @Test
    public void loadUserByUsername_WhenACustomerIsFoundThroughCpfAUserReturns() {
        when(customerRepository.findByCpf("10575823607")).thenReturn(customer);
        UserDetails userSS = userDetailsService.loadUserByUsername("10575823607");
        assertNotNull(userSS);
    }

    @Test
    public void loadUserByUsername_WhenACustomerIsNotFoundThroughCpfAnExceptionIsIssued() {
        when(customerRepository.findByCpf("10575823607")).thenReturn(null);
        exception.expect(UsernameNotFoundException.class);
        exception.expectMessage("10575823607");
        UserDetails userSS = userDetailsService.loadUserByUsername("10575823607");
        assertNull(userSS);
    }


}

