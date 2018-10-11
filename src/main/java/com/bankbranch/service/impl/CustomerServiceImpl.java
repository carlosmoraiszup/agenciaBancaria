package com.bankbranch.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankbranch.domain.Account;
import com.bankbranch.domain.Customer;
import com.bankbranch.dto.CustomerDTO;
import com.bankbranch.repository.AccountRepository;
import com.bankbranch.repository.ClienteRepository;
import com.bankbranch.service.AccountService;
import com.bankbranch.service.CustomerService;
import com.bankbranch.service.exception.EmptyException;
import com.bankbranch.service.exception.ExistingAccountException;
import com.bankbranch.service.exception.LengthCpfException;
import com.bankbranch.service.exception.ObjectNotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;


    public Customer findIdCustomer(Integer id) {
        Optional<Customer> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException("Customer not found!"));
    }

    @Override
    public Customer registerCustomer(Customer customer) {

        if (!isRegister(customer))
            throw new ExistingAccountException("CPF already registered!");

        ValidationsImpl.validationName(customer.getNameCustomer());
        ValidationsImpl.validationCpf(customer.getCpf());

        Customer newCustomer = new Customer();
        newCustomer.setId(null);
        newCustomer.setNameCustomer(customer.getNameCustomer());
        newCustomer.setCpf(customer.getCpf());
        newCustomer.setDateCreation(LocalDate.now().toString());
        Account account = accountService.registerAccount();

        if (account == null)
            throw new EmptyException("Account is null");

        newCustomer.setAccount(account);

        return clienteRepository.saveAndFlush(newCustomer);
    }

    private boolean isRegister(Customer customer) {

        if (null == customer) {
            return false;
        } else {
            if (null == customer.getCpf())
                throw new LengthCpfException("CPF should not be null!");

            if (customer.getCpf().isEmpty())
                throw new LengthCpfException("CPF must have exactly 11 digits!");

            customer.setCpf(ValidationsImpl.cleanCPF(customer.getCpf()));
            customer = clienteRepository.findByCpf(customer.getCpf());
            if (customer != null && customer.getCpf() != null)
                return false;
        }

        return true;

    }

    @Override
    public List<Customer> findAllCustomer() {
        return clienteRepository.findAll();
    }


    @Override
    public void deleteCustomer(Integer id) {
        findIdCustomer(id);
        clienteRepository.deleteById(id);
        accountRepository.deleteById(id);
    }

    @Override
    public Customer updateCustomer(Customer newCustomer) {
        Customer customerFound = findIdCustomer(newCustomer.getId());
        if (null != newCustomer.getCpf()) {
            ValidationsImpl.validationCpf(newCustomer.getCpf());
            customerFound.setCpf(ValidationsImpl.cleanCPF(newCustomer.getCpf()));
        }
        if (null != newCustomer.getNameCustomer()) {
            ValidationsImpl.validationName(newCustomer.getNameCustomer());
            customerFound.setNameCustomer(newCustomer.getNameCustomer());
        }
        return clienteRepository.saveAndFlush(customerFound);
    }

    @Override
    public Customer fromDTO(CustomerDTO customerDTO) {
        return new Customer(customerDTO.getId(), customerDTO.getNameCustomer(), customerDTO.getCpf(), null,
                customerDTO.getDateCreated());
    }


}