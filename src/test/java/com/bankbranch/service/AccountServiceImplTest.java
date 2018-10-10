package com.bankbranch.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.bankbranch.AbstractTest;
import com.bankbranch.domain.Account;
import com.bankbranch.repository.AccountRepository;
import com.bankbranch.service.impl.AccountServiceImpl;


public class AccountServiceImplTest extends AbstractTest {


    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepository;

    private Account account;

    @Before
    public void setUP() {
        account = new Account(1, "26/08/2018", 180.12);
    }

    //registerAccount
    @Test
    public void registerAccountOK() {
        when(accountService.registerAccount()).thenReturn(account);
        ArgumentCaptor<Account> arg = ArgumentCaptor.forClass(Account.class);
        accountService.registerAccount();
        verify(accountRepository, times(1)).saveAndFlush(arg.capture());
    }
}

