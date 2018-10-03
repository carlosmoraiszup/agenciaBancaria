package com.agenciaBancaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.service.ClienteService;
import com.agenciaBancaria.service.ContaService;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<Cliente> registerCustomer(@RequestBody Cliente cliente) {
        cliente = contaService.registerAccount(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> findAll() {
        List<Cliente> list = clienteService.findAllCustomer();
        return ResponseEntity.ok().body(list);
    }
}