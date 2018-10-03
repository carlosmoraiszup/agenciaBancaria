package com.agenciaBancaria.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.repository.ClienteRepository;
import com.agenciaBancaria.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    private Timestamp data = new Timestamp(System.currentTimeMillis());
    private String date = new SimpleDateFormat("dd/MM/yyyy").format(data.getTime());

    @Override
    public Cliente registerCustomer(Cliente cliente, Conta conta) {

        if(null == conta)
            return null;

        cliente.setId(null);
        cliente.setDataCriacao(date);
        cliente.setConta(conta);

        return clienteRepository.save(cliente);


    }

    @Override
    public List<Cliente> findAllCustomer() {
        return clienteRepository.findAll();
    }
}