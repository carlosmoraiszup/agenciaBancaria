package com.agenciaBancaria.service;

import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.repository.ContaRepository;
import com.agenciaBancaria.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class OperacaoService {


    @Autowired
    private ContaRepository contaService;


    public Conta find(Integer id) {
        Optional<Conta> obj = contaService.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Conta.class.getName()));
    }


    public Optional<Conta> buscarSaldo(Integer id) {
        Optional<Conta> c = contaService.findById(id);
        return c;
    }


    public Conta update(Conta obj) {
        Conta newObj = find(obj.getId());
        updateDate(newObj, obj);
        return contaService.save(newObj);
    }

    private void updateDate(Conta newObj, Conta obj){
        Double saldoAnterior = newObj.getSaldo() + obj.getSaldo();
        newObj.setSaldo(saldoAnterior);
    }



}
