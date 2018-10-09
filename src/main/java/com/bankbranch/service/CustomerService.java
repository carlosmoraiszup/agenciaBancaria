package com.bankbranch.service;

import java.util.List;

import com.bankbranch.domain.Customer;
import com.bankbranch.domain.Account;
import com.bankbranch.dto.CustomerDTO;

public interface CustomerService {

    Customer registerCustomer(Customer customer, Account account);

    List<Customer> findAllCustomer();

    void deleteCustomer(Integer id);

    Customer updateCustomer(Customer customer);

    Customer fromDTO(CustomerDTO customerDTO);

}
