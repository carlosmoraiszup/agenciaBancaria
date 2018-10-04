package com.agenciaBancaria.service;

import java.util.List;
import java.util.Optional;

import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.domain.Operacao;
import com.agenciaBancaria.domain.enums.TipoOperacao;

public interface OperacaoService {

    Conta findAccount(Integer id);

    Operacao typeOperation(Operacao operacao);

//    void depositDate(Conta atualizarConta, Operacao objRecebido);
//
//    void sakeDate(Conta atualizarConta, Operacao objRecebido);

    List<Operacao> findExtract(Integer id);
}