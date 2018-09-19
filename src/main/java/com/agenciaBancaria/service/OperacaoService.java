package com.agenciaBancaria.service;

import com.agenciaBancaria.domain.Operacao;
import com.agenciaBancaria.repository.OperacaoRepository;
import com.agenciaBancaria.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OperacaoService {

    @Autowired
    private OperacaoRepository repo;

    public Operacao buscarSaldo(Integer id) {
        Optional<Operacao> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Operacao.class.getName()));
    }

}
