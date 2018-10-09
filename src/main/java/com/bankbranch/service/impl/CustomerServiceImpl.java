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
import com.bankbranch.service.CustomerService;
import com.bankbranch.service.exception.ObjectNotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AccountRepository accountRepository;


    public Customer findIdCustomer(Integer id) {
        Optional<Customer> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException("Customer not found!"));
    }

    @Override
    public Customer registerCustomer(Customer customer, Account account) {

        if (null == account)
            return null;

        customer.setId(null);
        customer.setDateCreation(LocalDate.now().toString());
        customer.setAccount(account);

        return clienteRepository.save(customer);

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
            Validations.validationCpf(newCustomer.getCpf());
            customerFound.setCpf(newCustomer.getCpf());
        }
        if (null != newCustomer.getNameCustomer()) {
            Validations.validationName(newCustomer.getNameCustomer());
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