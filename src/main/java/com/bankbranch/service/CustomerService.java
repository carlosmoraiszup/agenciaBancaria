package com.bankbranch.service;

import java.util.List;

import com.bankbranch.domain.Customer;
import com.bankbranch.dto.CustomerDTO;

public interface CustomerService {

    CustomerDTO registerCustomer(Customer customer);

    List<CustomerDTO> findAllCustomer();

    CustomerDTO findCpfCustomer(String cpf);

    void deleteCustomer(String cpf);

    CustomerDTO updateCustomer(Customer customer);

}
