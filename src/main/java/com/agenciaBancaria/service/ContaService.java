package com.agenciaBancaria.service;

import java.util.Optional;

import org.hibernate.TransientPropertyValueException;

import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.domain.Conta;

public interface ContaService {

    Cliente registerAccount(Cliente cliente);

    Optional<Conta> buscarSaldo(Integer id);

}
