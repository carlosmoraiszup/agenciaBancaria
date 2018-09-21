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
        return c;
    }


    public Conta operacao(Operacao objRecebido, TipoOperacao tipoOperacao) {
        Conta atualizarConta1  = find(objRecebido.getIdDestino());

        if (tipoOperacao.equals(TipoOperacao.DEPOSITO)) {
            depositoDate(atualizarConta1, objRecebido, TipoOperacao.DEPOSITO);
        }
        if (tipoOperacao.equals(TipoOperacao.SAQUE)) {
            saqueDate(atualizarConta1, objRecebido, TipoOperacao.SAQUE);
        }
        if (tipoOperacao.equals(TipoOperacao.TRANSFERENCIA)){
            Conta atualizarConta2 = find(objRecebido.getId_Origem());
            saqueDate(atualizarConta2, objRecebido, TipoOperacao.TRANSFERENCIA);
            depositoDate(atualizarConta1, objRecebido, TipoOperacao.TRANSFERENCIA);
            contaService.save(atualizarConta2);

        }
        return contaService.save(atualizarConta1);
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
        return obj;
    }
}
