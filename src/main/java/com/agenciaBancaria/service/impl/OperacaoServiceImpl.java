package com.agenciaBancaria.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.domain.Operacao;
import com.agenciaBancaria.domain.enums.TipoOperacao;
import com.agenciaBancaria.repository.ContaRepository;
import com.agenciaBancaria.repository.OperacaoRepository;
import com.agenciaBancaria.service.OperacaoService;
import com.agenciaBancaria.service.exception.EqualAccountTransfer;
import com.agenciaBancaria.service.exception.ObjectNotFoundException;
import com.agenciaBancaria.service.exception.UnprocessableEntity;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OperacaoServiceImpl implements OperacaoService {

    private Timestamp data = new Timestamp(System.currentTimeMillis());
    private String date = new SimpleDateFormat("dd/MM/yyyy").format(data.getTime());

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private OperacaoRepository operacaoRepository;


    @Override
    public Conta findAccount(Integer id) {
        Optional<Conta> buscarConta = contaRepository.findById(id);
        return buscarConta.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Conta.class.getName()));
    }


    @Override
    public Operacao typeOperation(Operacao operacao) {
        if (operacao.getTipoOperacao().equals(TipoOperacao.DEPOSITO)) {
            Conta contaDeposito = findAccount(operacao.getIdContaDestino());
            depositDate(contaDeposito, operacao);
            contaRepository.saveAndFlush(contaDeposito);
        }
        if (operacao.getTipoOperacao().equals(TipoOperacao.SAQUE)) {
            Conta contaSaque = findAccount(operacao.getIdContaOrigem());
            sakeDate(contaSaque, operacao);
            contaRepository.saveAndFlush(contaSaque);
        }
        if (operacao.getTipoOperacao().equals(TipoOperacao.TRANSFERENCIA)) {
            if (operacao.getIdContaDestino() != operacao.getIdContaOrigem()) {
                Conta contaSaque = findAccount(operacao.getIdContaOrigem());
                sakeDate(contaSaque, operacao);

                Conta contaDeposito = findAccount(operacao.getIdContaDestino());
                depositDate(contaDeposito, operacao);

                contaRepository.saveAndFlush(contaSaque);
                contaRepository.saveAndFlush(contaDeposito);
            } else {
                throw new EqualAccountTransfer("Proibida transferência para mesma conta!");
            }
        }
        return operacao;
    }



    private void depositDate(Conta conta, Operacao operacao) {
        Double saldo = conta.getSaldo() + operacao.getValor();
        conta.setSaldo(saldo);
        operacao.setDataOperacao(date);
        operacaoRepository.saveAndFlush(operacao);

    }


    private void sakeDate(Conta conta, Operacao operacao) {
        Double saldo = conta.getSaldo() - operacao.getValor();
        if (saldo >= 0) {
            conta.setSaldo(saldo);
            operacao.setDataOperacao(date);
            operacaoRepository.saveAndFlush(operacao);
        } else {
            throw new UnprocessableEntity("Saldo indisponível!");
        }
    }

    @Override
    public List<Operacao> findExtract(Integer id) {
        List<Operacao> listOperacoes = operacaoRepository.operacao(id);
        for (Operacao operacao : listOperacoes) {
            if (operacao.getTipoOperacao().equals(TipoOperacao.TRANSFERENCIA) && operacao.getIdContaOrigem() == id) {
                operacao.setValor(operacao.getValor() - (2 * operacao.getValor()));
            }
        }
        return listOperacoes;
    }
}
