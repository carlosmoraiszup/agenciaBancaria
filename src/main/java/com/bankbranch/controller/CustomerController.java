package com.bankbranch.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO registerCustomer(@Valid @RequestBody Customer customer) {
        return customerService.registerCustomer(customer);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/findAll")
    public List<CustomerDTO> findAllCustomers() {
        return customerService.findAllCustomer();
    }

    @GetMapping(value = "/viewProfileData")
    public CustomerDTO findCpfCustomer() {
        return customerService.viewProfileData();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/deleteByCPF/{cpf}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String cpf) {
        customerService.deleteCustomer(cpf);
        return ResponseEntity.accepted().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/update")
    public CustomerDTO updateCustomer(@Valid @RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }
}