package com.agenciaBancaria.service;

import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.domain.Operacao;
import com.agenciaBancaria.domain.enums.TipoOperacao;
import com.agenciaBancaria.repository.ContaRepository;
import com.agenciaBancaria.repository.OperacaoRepository;
import com.agenciaBancaria.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;


@Service
public class OperacaoService {


    @Autowired
    private ContaRepository contaService;

    @Autowired
    private OperacaoRepository operacaoRepository;

    Timestamp data = new Timestamp(System.currentTimeMillis());
    String date = new SimpleDateFormat("dd/MM/yyyy").format(data.getTime());

    public Conta find(Integer id) {
        Optional<Conta> obj = contaService.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Conta.class.getName()));
    }

    public Optional<Conta> buscarSaldo(Integer id) {
        Optional<Conta> c = contaService.findById(id);
        return Optional.ofNullable(c.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Conta.class.getName())));
    }


    public Operacao operacao(Operacao objRecebido, TipoOperacao tipoOperacao) {

        if (tipoOperacao.equals(TipoOperacao.DEPOSITO)) {
            Conta atualizarContaDeposito  = find(objRecebido.getIdContaDestino());
            depositoDate(atualizarContaDeposito, objRecebido, TipoOperacao.DEPOSITO);
            contaService.save(atualizarContaDeposito);
        }
        if (tipoOperacao.equals(TipoOperacao.SAQUE)) {
            Conta atualizarContaSaque  = find(objRecebido.getIdContaOrigem());
            saqueDate(atualizarContaSaque, objRecebido, TipoOperacao.SAQUE);
            contaService.save(atualizarContaSaque);
        }
        if (tipoOperacao.equals(TipoOperacao.TRANSFERENCIA)){
            Conta atualizarContaDeposito  = find(objRecebido.getIdContaDestino());
            Conta atualizarContaSaque  = find(objRecebido.getIdContaOrigem());
            saqueDate(atualizarContaSaque, objRecebido, TipoOperacao.TRANSFERENCIA);
            depositoDate(atualizarContaDeposito, objRecebido, TipoOperacao.TRANSFERENCIA);
            contaService.save(atualizarContaDeposito);
            contaService.save(atualizarContaSaque);

        }

        return objRecebido;

    }

    private void depositoDate(Conta atualizarConta, Operacao objRecebido, TipoOperacao tipo) {
        Double saldo = atualizarConta.getSaldo() + objRecebido.getValor();
        atualizarConta.setSaldo(saldo);
        objRecebido.setDataOperacao(date);
        if(tipo.equals(TipoOperacao.DEPOSITO)){
            objRecebido.setTipoOperacao(tipo);
        }else{
            objRecebido.setTipoOperacao(tipo);
        }
        operacaoRepository.save(objRecebido);

    }

    private void saqueDate(Conta atualizarConta, Operacao objRecebido, TipoOperacao tipo) {
        Double saldo = atualizarConta.getSaldo() - objRecebido.getValor();
        if (saldo >= 0) {
            atualizarConta.setSaldo(saldo);
            objRecebido.setDataOperacao(date);
            if(tipo.equals(TipoOperacao.SAQUE)){
                objRecebido.setTipoOperacao(tipo);
            }else{
                objRecebido.setTipoOperacao(tipo);
            }
            operacaoRepository.save(objRecebido);
        } else {
            throw new ObjectNotFoundException("Saldo indisponível");
        }
    }


    public List<Operacao> findExtrato(Integer id) {
        List<Operacao> obj = operacaoRepository.operacao(id);
        for (Operacao x:obj) {
            if(x.getTipoOperacao().equals(TipoOperacao.TRANSFERENCIA) && x.getIdContaOrigem() == id){
                x.setValor(x.getValor()-(2*x.getValor()));
            }
        }
        return obj;
    }
}
