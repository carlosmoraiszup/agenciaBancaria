package com.agenciaBancaria.service;

import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ClienteService {


    @Autowired
    private ClienteRepository repo;

    Timestamp data = new Timestamp(System.currentTimeMillis());
    String date = new SimpleDateFormat("dd/MM/yyyy").format(data.getTime());

    public Cliente registerCustomer(Cliente obj) {
        obj.setId(null);
        obj.setDataCriacao(date);
        return repo.save(obj);
    }

    public List<Cliente> findAll(){
        return repo.findAll();
    }

}
