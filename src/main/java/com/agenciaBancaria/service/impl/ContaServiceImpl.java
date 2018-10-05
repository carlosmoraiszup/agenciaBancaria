package com.agenciaBancaria.service.impl;

import java.time.LocalDate;

import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.repository.ClienteRepository;
import com.agenciaBancaria.repository.ContaRepository;
import com.agenciaBancaria.service.ClienteService;
import com.agenciaBancaria.service.ContaService;


@Service
public class ContaServiceImpl implements ContaService{

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Cliente registerAccount(Cliente cliente) throws TransientPropertyValueException {

        if(clienteRepository.findByCpf(cliente.getCpf()) == null) {

            Conta conta = new Conta(null, LocalDate.now().toString(), 0.0);
            contaRepository.saveAndFlush(conta);
            cliente = clienteService.registerCustomer(cliente, conta);
            return cliente;
        }

            throw new NullPointerException("CPF já cadastrado!");
    }


}
