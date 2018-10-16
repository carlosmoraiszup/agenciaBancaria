package com.bankbranch.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bankbranch.domain.Customer;
import com.bankbranch.dto.CustomerDTO;
import com.bankbranch.service.CustomerService;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/registerCustomer")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO registerCustomer(@Valid @RequestBody Customer customer) {
        customer = customerService.registerCustomer(customer);
        CustomerDTO customerDTO = new CustomerDTO(customer);
        return customerDTO;
    }

    @GetMapping(value = "/findAllCustomers")
    public List<Customer> findAllCustomers() {
        List<Customer> listCustomer = customerService.findAllCustomer();
        return listCustomer;
    }

    @DeleteMapping(value = "/deleteCustomer/{cpf}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String cpf) {
        customerService.deleteCustomer(cpf);
        return ResponseEntity.accepted().build();
    }

    @PutMapping(value = "/updateCustomer")
    public CustomerDTO updateCustomer(@Valid @RequestBody Customer customer) {
        customer = customerService.updateCustomer(customer);
        CustomerDTO customerDTO = new CustomerDTO(customer);
        return customerDTO;
    }
}