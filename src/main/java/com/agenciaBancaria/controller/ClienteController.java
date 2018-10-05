package com.agenciaBancaria.controller;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.dto.ClienteDTO;
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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id){
        clienteService.deleteCustomer(id);
        return ResponseEntity.ok().body("Cliente deletado!");
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Cliente> update(@Valid @RequestBody ClienteDTO cliente, @PathVariable Integer id){
        Cliente c = clienteService.fromDTO(cliente);
        c.setId(id);
        c = clienteService.updateCustomer(c);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }
}