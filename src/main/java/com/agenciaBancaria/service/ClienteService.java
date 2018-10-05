package com.agenciaBancaria.service;

import java.util.List;

import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.dto.ClienteDTO;

public interface ClienteService {

    Cliente registerCustomer(Cliente cliente, Conta conta);

    List<Cliente> findAllCustomer();

    void deleteCustomer(Integer id);

    Cliente updateCustomer(Cliente cliente);

    Cliente fromDTO(ClienteDTO clienteDTO);

}
