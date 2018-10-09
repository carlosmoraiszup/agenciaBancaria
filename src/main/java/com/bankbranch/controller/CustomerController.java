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
import org.springframework.web.bind.annotation.RestController;

import com.bankbranch.domain.Customer;
import com.bankbranch.dto.CustomerDTO;
import com.bankbranch.service.CustomerService;
import com.bankbranch.service.AccountService;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        customer = accountService.registerAccount(customer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> findAllCustomer() {
        List<Customer> listCustomer = customerService.findAllCustomer();
        return ResponseEntity.ok().body(listCustomer);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id){
        customerService.deleteCustomer(id);
        return ResponseEntity.accepted().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody CustomerDTO customerDTO,
            @PathVariable Integer id){
        Customer customer = customerService.fromDTO(customerDTO);
        customer.setId(id);
        customer = customerService.updateCustomer(customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}