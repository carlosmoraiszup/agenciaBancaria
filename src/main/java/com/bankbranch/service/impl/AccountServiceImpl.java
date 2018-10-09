package com.bankbranch.service.impl;

import java.time.LocalDate;

import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankbranch.domain.Account;
import com.bankbranch.domain.Customer;
import com.bankbranch.repository.ClienteRepository;
import com.bankbranch.repository.AccountRepository;
import com.bankbranch.service.CustomerService;
import com.bankbranch.service.AccountService;
import com.bankbranch.service.exception.EmptyException;
import com.bankbranch.service.exception.ExistingAccountException;
import com.bankbranch.service.exception.LengthCpfException;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Customer registerAccount(Customer customer) {

        if(clienteRepository.findByCpf(customer.getCpf()) == null) {
            if(customer.getNameCustomer() == null || customer.getNameCustomer().isEmpty())
                throw new EmptyException("Name is null!");

            if(customer.getCpf().length() == 11) {
                Account account = new Account(null, LocalDate.now().toString(), 0.0);
                accountRepository.saveAndFlush(account);
                customer = customerService.registerCustomer(customer, account);
                   return customer;
            }else{
                throw new LengthCpfException("There must be exactly 11 digits!");
            }
        }
            throw new ExistingAccountException("CPF already registered!");
    }


}
