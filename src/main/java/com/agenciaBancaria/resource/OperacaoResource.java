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
    public ResponseEntity<Optional<Conta>> find(@PathVariable Integer id) {
        Optional<Conta> obj = service.buscarSaldo(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value = "/deposito", method = RequestMethod.POST)
    public ResponseEntity<Operacao> deposito(@RequestBody Operacao obj) {
        Operacao newObj = service.operacao(obj, TipoOperacao.DEPOSITO);
        return ResponseEntity.ok().body(newObj);
    }

    @RequestMapping(value = "/saque", method = RequestMethod.POST)
    public ResponseEntity<Operacao> saque(@RequestBody Operacao obj) {
        Operacao newObj = service.operacao(obj, TipoOperacao.SAQUE);
        return ResponseEntity.ok().body(newObj);
    }

    @RequestMapping(value = "/transferencia", method = RequestMethod.POST)
    public ResponseEntity<Operacao> transferencia(@RequestBody Operacao obj) {
        Operacao newObj = service.operacao(obj, TipoOperacao.TRANSFERENCIA);
        return ResponseEntity.ok().body(newObj);
    }


    @RequestMapping(value = "extrato/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Operacao>> extrato(@PathVariable Integer id){
        List<Operacao> obj = service.findExtrato(id);
        return ResponseEntity.ok().body(obj);
    }

}