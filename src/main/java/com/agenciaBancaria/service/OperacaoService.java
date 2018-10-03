package com.agenciaBancaria.service;

import java.util.List;
import java.util.Optional;

import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.domain.Operacao;
import com.agenciaBancaria.domain.enums.TipoOperacao;

public interface OperacaoService {

    Optional<Conta> buscarSaldo(Integer id);

    Operacao tipoOperacao(Operacao objRecebido, TipoOperacao tipoOperacao);

    void depositoDate(Conta atualizarConta, Operacao objRecebido, TipoOperacao tipo);

    void saqueDate(Conta atualizarConta, Operacao objRecebido, TipoOperacao tipo);

    List<Operacao> findExtrato(Integer id);
}