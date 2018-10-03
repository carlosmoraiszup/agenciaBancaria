package com.agenciaBancaria.service;

import java.util.List;
import java.util.Optional;

import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.domain.Operacao;
import com.agenciaBancaria.domain.enums.TipoOperacao;

public interface OperacaoService {

    Conta findAccount(Integer id);

    Operacao typeOperation(Operacao objRecebido, TipoOperacao tipoOperacao);

    void depositDate(Conta atualizarConta, Operacao objRecebido, TipoOperacao tipo);

    void sakeDate(Conta atualizarConta, Operacao objRecebido, TipoOperacao tipo);

    List<Operacao> findExtract(Integer id);
}