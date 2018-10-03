package com.agenciaBancaria.service;

import java.util.List;

import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.domain.Conta;

public interface ClienteService {


    Cliente registerCustomer(Cliente cliente, Conta conta);

    List<Cliente> findAllCustomer();



}
