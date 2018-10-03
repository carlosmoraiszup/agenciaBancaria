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
    private ContaRepository contaService;

    @Autowired
    private OperacaoRepository operacaoRepository;


    @Override
    public Conta findAccount(Integer id) {
        Optional<Conta> buscarConta = contaService.findById(id);
        return buscarConta.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Conta.class.getName()));
    }


    @Override
    public Operacao typeOperation(Operacao operacao, TipoOperacao tipoOperacao) {
        if (tipoOperacao.equals(TipoOperacao.DEPOSITO)) {
            Conta atualizarContaDeposito = findAccount(operacao.getIdContaDestino());
            depositDate(atualizarContaDeposito, operacao, TipoOperacao.DEPOSITO);
            contaService.save(atualizarContaDeposito);
        }
        if (tipoOperacao.equals(TipoOperacao.SAQUE)) {
            Conta atualizarContaSaque = findAccount(operacao.getIdContaOrigem());
            sakeDate(atualizarContaSaque, operacao, TipoOperacao.SAQUE);
            contaService.save(atualizarContaSaque);
        }
        if (tipoOperacao.equals(TipoOperacao.TRANSFERENCIA)) {
            if (operacao.getIdContaDestino() != operacao.getIdContaOrigem()) {
                Conta atualizarContaDeposito = findAccount(operacao.getIdContaDestino());
                Conta atualizarContaSaque = findAccount(operacao.getIdContaOrigem());
                sakeDate(atualizarContaSaque, operacao, TipoOperacao.TRANSFERENCIA);
                depositDate(atualizarContaDeposito, operacao, TipoOperacao.TRANSFERENCIA);
                contaService.save(atualizarContaDeposito);
                contaService.save(atualizarContaSaque);
            } else {
                throw new EqualAccountTransfer("Proibida transferência para mesma conta!");
            }
        }
        return operacao;
    }

    @Override
    public void depositDate(Conta atualizarConta, Operacao operacao, TipoOperacao tipo) {
        Double saldo = atualizarConta.getSaldo() + operacao.getValor();
        atualizarConta.setSaldo(saldo);
        operacao.setDataOperacao(date);
        if (tipo.equals(TipoOperacao.DEPOSITO)) {
            operacao.setTipoOperacao(tipo);
        } else {
            operacao.setTipoOperacao(tipo);
        }
        operacaoRepository.save(operacao);

    }


    @Override
    public void sakeDate(Conta atualizarConta, Operacao operacao, TipoOperacao tipo) {
        Double saldo = atualizarConta.getSaldo() - operacao.getValor();
        if (saldo >= 0) {
            atualizarConta.setSaldo(saldo);
            operacao.setDataOperacao(date);
            if (tipo.equals(TipoOperacao.SAQUE)) {
                operacao.setTipoOperacao(tipo);
            } else {
                operacao.setTipoOperacao(tipo);
            }
            operacaoRepository.save(operacao);
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
