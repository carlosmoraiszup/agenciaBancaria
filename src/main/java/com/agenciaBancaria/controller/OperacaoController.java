package com.agenciaBancaria.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public ResponseEntity<Optional<Conta>> buscarSaldo(@PathVariable Integer id) {
        Optional<Conta> obj = operacaoService.buscarSaldo(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping(value = "/deposito")
    public ResponseEntity<OperacaoDepositoDTO> deposito(@RequestBody Operacao obj) {
        Operacao newObj = operacaoService.tipoOperacao(obj, TipoOperacao.DEPOSITO);
        OperacaoDepositoDTO s = new OperacaoDepositoDTO(newObj);
        return ResponseEntity.ok().body(s);
    }

    @RequestMapping(value = "/saque", method = RequestMethod.POST)
    public ResponseEntity<OperacaoSaqueDTO> saque(@RequestBody Operacao obj) {
        Operacao newObj = operacaoService.tipoOperacao(obj, TipoOperacao.SAQUE);
        OperacaoSaqueDTO s = new OperacaoSaqueDTO(newObj);
        return ResponseEntity.ok().body(s);
    }

    @RequestMapping(value = "/transferencia", method = RequestMethod.POST)
    public ResponseEntity<Operacao> transferencia(@RequestBody Operacao obj) {
        Operacao newObj = operacaoService.tipoOperacao(obj, TipoOperacao.TRANSFERENCIA);
        return ResponseEntity.ok().body(newObj);
    }


    @GetMapping(value = "extrato/{id}")
    public ResponseEntity<List<OperacaoExtratoDTO>> extrato(@PathVariable Integer id){
        List<Operacao> obj = operacaoService.findExtrato(id);
        List<OperacaoExtratoDTO> newObj =
                obj.stream().map(s -> new OperacaoExtratoDTO(s)).collect(Collectors.toList());
        return ResponseEntity.ok().body(newObj);
    }

}