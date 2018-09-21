package com.agenciaBancaria.resource;

import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.service.ClienteService;
import com.agenciaBancaria.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/cliente")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ContaService contaService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> registerCustomer(@RequestBody Cliente obj){
        obj = clienteService.registerCustomer(obj);
        contaService.cadastrarConta(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Cliente>> findAll(){
        List<Cliente> list = clienteService.findAll();
        return ResponseEntity.ok().body(list);
    }



}
