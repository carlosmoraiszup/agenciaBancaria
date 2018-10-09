
package com.agenciaBancaria.service;

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

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.agenciaBancaria.AbstractTest;
import com.bankbranch.domain.Account;
import com.bankbranch.domain.Customer;
import com.bankbranch.repository.ClienteRepository;
import com.bankbranch.repository.AccountRepository;
import com.bankbranch.service.CustomerService;
import com.bankbranch.service.impl.AccountServiceImpl;


public class AccountServiceImplTest extends AbstractTest {


    @InjectMocks
    private AccountServiceImpl contaService;

    @Mock
    private CustomerService customerService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ClienteRepository clienteRepository;

    private Account account;
    private Customer customer;

    @Before
    public void setUP() {

      account = new Account(1, "26/08/2018", 180.12);
      customer = new Customer(1, "Carlos", "11122233344", account, "01/01/2018");


    }



    //registerAccount
    @Test
    public void registerAccountEXCEPTION() {

        when(clienteRepository.findByCpf(anyString())).thenReturn(customer);
        exception.expect(NullPointerException.class);

        contaService.registerAccount(customer);


    }

    @Test
    public void registerAccountOK() {

        when(customerService.registerCustomer(any(Customer.class), any(Account.class))).thenReturn(customer);
        when(clienteRepository.findByCpf(anyString())).thenReturn(null);
        ArgumentCaptor<Account> arg = ArgumentCaptor.forClass(Account.class);

        Customer customerResponse = contaService.registerAccount(customer);

        verify(accountRepository, times(1)).saveAndFlush(arg.capture());


    }

}

