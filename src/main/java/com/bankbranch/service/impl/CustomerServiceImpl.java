package com.bankbranch.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankbranch.controller.exception.ResourceExceptionHandler;
import com.bankbranch.domain.Account;
import com.bankbranch.domain.Customer;
import com.bankbranch.repository.AccountRepository;
import com.bankbranch.repository.CustomerRepository;
import com.bankbranch.service.AccountService;
import com.bankbranch.service.CustomerService;
import com.bankbranch.service.exception.EmptyException;
import com.bankbranch.service.exception.ExistingCustomerException;
import com.bankbranch.service.exception.ObjectNotFoundException;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    ResourceExceptionHandler resourceExceptionHandler;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;

    public Customer findIdCustomer(Integer id) {
        Optional<Customer> cliente = customerRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException("Customer not found!"));
    }

    @Override
    public Customer registerCustomer(Customer customer) {

        if(customerRepository.findByCpf(customer.getCpf()) != null)
            throw new ExistingCustomerException("CPF is already registered!");

        Customer newCustomer = new Customer();
        newCustomer.setId(null);
        newCustomer.setNameCustomer(customer.getNameCustomer());
        newCustomer.setCpf(customer.getCpf());
        newCustomer.setDateCreation(LocalDate.now().toString());
        Account account = accountService.registerAccount();

        if (account == null)
            throw new EmptyException("Account is null");

        newCustomer.setAccount(account);

        return customerRepository.saveAndFlush(newCustomer);

    }


    @Override
    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomer(String cpf) {
        Customer customer = customerRepository.findByCpf(cpf);
        if(customer == null)
            throw new ObjectNotFoundException("CPF not registered!");

        customerRepository.deleteById(customer.getId());
        accountRepository.deleteById(customer.getAccount().getId());
    }

    @Override
    public Customer updateCustomer(Customer newCustomer) {
        Customer customerFound = customerRepository.findByCpf(newCustomer.getCpf());
        if (customerFound != null) {
            customerFound.setNameCustomer(newCustomer.getNameCustomer());
            customerFound.setCpf(newCustomer.getCpf());
        }
        return customerRepository.saveAndFlush(customerFound);
    }

}