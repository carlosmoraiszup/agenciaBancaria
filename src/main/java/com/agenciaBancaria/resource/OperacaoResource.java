package com.agenciaBancaria.resource;

import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.domain.Operacao;
import com.agenciaBancaria.domain.enums.TipoOperacao;
import com.agenciaBancaria.service.ContaService;
import com.agenciaBancaria.service.OperacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/operacao")
public class OperacaoResource {

    @Autowired
    private OperacaoService service;

    @Autowired
    private ContaService contaService;


    @RequestMapping(value = "/buscarSaldo/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> find(@PathVariable Integer id) {
        Optional<Conta> obj = service.buscarSaldo(id);
        String saldo = "Conta: " + obj.get().getId() + ", possui saldo de: " + obj.get().getSaldo() + "R$";
        return ResponseEntity.ok().body(saldo);
    }

    @RequestMapping(value = "/deposito", method = RequestMethod.POST)
    public ResponseEntity<String> deposito(@RequestBody Operacao obj) {
        service.operacao(obj, TipoOperacao.DEPOSITO);
        String msg = "Depósito no VALOR:" + obj.getValor() + "R$ para CONTA:" + obj.getIdDestino() + " realizado com sucesso!";
        return ResponseEntity.ok().body(msg);
    }

    @RequestMapping(value = "/saque", method = RequestMethod.POST)
    public ResponseEntity<String> saque(@RequestBody Operacao obj) {
        service.operacao(obj, TipoOperacao.SAQUE);
        String msg = "Saque no VALOR:" + obj.getValor() + "R$ na CONTA:" + obj.getIdDestino() + " realizado com sucesso!";
        return ResponseEntity.ok().body(msg);
    }

    @RequestMapping(value = "/transferencia", method = RequestMethod.POST)
    public ResponseEntity<String> transferencia(@RequestBody Operacao obj) {
        service.operacao(obj, TipoOperacao.TRANSFERENCIA);
        String msg = "Transferência no VALOR:" + obj.getValor() + "R$ da CONTA:" + obj.getId_Origem() + " para CONTA:" + obj.getIdDestino() + " realizado com sucesso!";
        return ResponseEntity.ok().body(msg);
    }


    @RequestMapping(value = "extrato/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Operacao>> extrato(@PathVariable Integer id){
        List<Operacao> obj = service.findExtrato(id);
        return ResponseEntity.ok().body(obj);
    }

}