package com.agenciaBancaria.service;

import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ContaRepository repo;

    Timestamp data = new Timestamp(System.currentTimeMillis());
    String date = new SimpleDateFormat("dd/MM/yyyy").format(data.getTime());

    public void cadastrarConta(Cliente obj) {
        Conta conta = new Conta(null, date, 0.0, obj);
        repo.saveAll(Arrays.asList(conta));
    }

    public Optional<Conta> buscarSaldo(Integer id){
        return  repo.findById(id);
    }


}
