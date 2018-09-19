package com.agenciaBancaria.service;

import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class OperacaoService {


    @Autowired
    private ContaRepository contaService;

    public Optional<Conta> buscarSaldo(Integer id) {
        Optional<Conta> c = contaService.findById(id);
        return c;
    }

}
