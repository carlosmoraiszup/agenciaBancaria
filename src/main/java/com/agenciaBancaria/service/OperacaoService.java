package com.agenciaBancaria.service;

import java.util.List;
import java.util.Optional;

import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.domain.Operacao;
import com.agenciaBancaria.domain.enums.TipoOperacao;

public interface OperacaoService {

    Conta findAccount(Integer id);

    Operacao typeOperation(Operacao operacao);

    List<Operacao> findExtract(Integer id);
}