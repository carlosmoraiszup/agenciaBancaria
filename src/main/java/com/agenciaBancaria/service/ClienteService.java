package com.agenciaBancaria.service;

import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {


    @Autowired
    private ClienteRepository repo;

    public Cliente registerCustomer(Cliente obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public List<Cliente> findAll(){
        return repo.findAll();
    }

}
