package com.bankbranch.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankbranch.domain.Account;
import com.bankbranch.repository.AccountRepository;
import com.bankbranch.service.AccountService;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public Account registerAccount() {
        Account account = new Account(null, LocalDate.now().toString(), 0.0);
        accountRepository.saveAndFlush(account);
        return account;
    }

}
