package com.agenciaBancaria.resource;

import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.service.ContaService;
import com.agenciaBancaria.service.OperacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/deposito/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> deposito(@RequestBody Conta obj, @PathVariable Integer id) {
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }


    /*@RequestMapping(value = "/deposito/{id}", method = RequestMethod.POST)
    public ResponseEntity<Void> deposito(@PathVariable Integer id, @RequestBody Conta obj){
        service.deposito(id, obj.getSaldo());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }*/



/*
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id){
        Categoria obj = service.fromDTO(objDTO);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CategoriaDTO>> findAll(){
        List<Categoria> list = service.findAll();
        List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }*/


}
