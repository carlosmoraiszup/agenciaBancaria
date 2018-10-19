package com.bankbranch.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankbranch.controller.exception.ResourceExceptionHandler;
import com.bankbranch.domain.Account;
import com.bankbranch.domain.Customer;
import com.bankbranch.domain.enums.Perfil;
import com.bankbranch.dto.CustomerDTO;
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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ResourceExceptionHandler resourceExceptionHandler;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;

    public CustomerDTO findCpfCustomer(String cpf) {
        Customer customer = customerRepository.findByCpf(cpf);
        if (null == customer)
            throw new ObjectNotFoundException("Customer not found!");
        return new CustomerDTO(customer);
    }

    @Override
    public CustomerDTO registerCustomer(Customer customer) {


        if (customerRepository.findByCpf(customer.getCpf()) != null)
            throw new ExistingCustomerException("CPF is already registered!");

        Customer newCustomer = new Customer();
        newCustomer.setId(null);
        newCustomer.setNameCustomer(customer.getNameCustomer());
        newCustomer.setCpf(customer.getCpf());
        newCustomer.setDateCreation(LocalDate.now().toString());
        newCustomer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        newCustomer.addPerfil(Perfil.CUSTOMER);
        Account account = accountService.registerAccount();

        if (account == null)
            throw new EmptyException("Account is null");

        newCustomer.setAccount(account);
        customerRepository.saveAndFlush(newCustomer);

        return new CustomerDTO(newCustomer);

    }


    @Override
    public List<CustomerDTO> findAllCustomer() {
        List<CustomerDTO> customerDTO = new ArrayList<>();
        customerRepository.findAll().forEach(customer -> {
            customerDTO.add(new CustomerDTO(customer));
        });
        return customerDTO;
    }

    @Override
    public void deleteCustomer(String cpf) {
        Customer customer = customerRepository.findByCpf(cpf);
        if (customer == null)
            throw new ObjectNotFoundException("CPF not registered!");


        accountRepository.deleteById(customer.getAccount().getNumberAccount());
        customerRepository.deleteById(customer.getId());

    }

    @Override
    public CustomerDTO updateCustomer(Customer newCustomer) {
        Customer customerFound = customerRepository.findByCpf(newCustomer.getCpf());
        if (customerFound == null)
            throw new ObjectNotFoundException("Customer not registered!");

        customerFound.setNameCustomer(newCustomer.getNameCustomer());
        customerFound.setCpf(newCustomer.getCpf());

        customerRepository.saveAndFlush(customerFound);

        return new CustomerDTO(customerFound);
    }

}