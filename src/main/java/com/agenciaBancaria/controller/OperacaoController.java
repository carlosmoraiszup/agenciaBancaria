package com.agenciaBancaria.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.domain.Operacao;
import com.agenciaBancaria.domain.enums.TipoOperacao;
import com.agenciaBancaria.dto.OperacaoDepositoDTO;
import com.agenciaBancaria.dto.OperacaoExtratoDTO;
import com.agenciaBancaria.dto.OperacaoSaqueDTO;
import com.agenciaBancaria.service.OperacaoService;

@RestController
@RequestMapping(value = "/operacao")
public class OperacaoController{

    @Autowired
    private OperacaoService operacaoService;

    @GetMapping(value = "/buscarSaldo/{id}")
    public ResponseEntity<Conta> buscarSaldo(@PathVariable Integer id) {
        Conta conta = operacaoService.findAccount(id);
        return ResponseEntity.ok().body(conta);
    }

    @PostMapping(value = "/deposito")
    public ResponseEntity<OperacaoDepositoDTO> deposito(@RequestBody Operacao operacao) {
        operacao.setTipoOperacao(TipoOperacao.DEPOSITO);
        Operacao newOperacao = operacaoService.typeOperation(operacao);
        OperacaoDepositoDTO operacaoDTO = new OperacaoDepositoDTO(newOperacao);
        return ResponseEntity.ok().body(operacaoDTO);
    }

    @PostMapping(value = "/saque")
    public ResponseEntity<OperacaoSaqueDTO> saque(@RequestBody Operacao operacao) {
        operacao.setTipoOperacao(TipoOperacao.SAQUE);
        Operacao newOperacao = operacaoService.typeOperation(operacao);
        OperacaoSaqueDTO operacaoDTO = new OperacaoSaqueDTO(newOperacao);
        return ResponseEntity.ok().body(operacaoDTO);
    }

    @PostMapping(value = "/transferencia")
    public ResponseEntity<Operacao> transferencia(@RequestBody Operacao operacao) {
        operacao.setTipoOperacao(TipoOperacao.TRANSFERENCIA);
        Operacao newObj = operacaoService.typeOperation(operacao);
        return ResponseEntity.ok().body(newObj);
    }


    @GetMapping(value = "extrato/{id}")
    public ResponseEntity<List<OperacaoExtratoDTO>> extrato(@PathVariable Integer id){
        List<Operacao> listOperacao = operacaoService.findExtract(id);
        List<OperacaoExtratoDTO> listOperacaoDTO =
                listOperacao.stream().map(newOperacao -> new OperacaoExtratoDTO(newOperacao)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listOperacaoDTO);
    }

}