package com.agenciaBancaria;

import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.repository.ClienteRepository;
import com.agenciaBancaria.repository.ContaRepository;
import com.agenciaBancaria.repository.OperacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class AgenciaBancariaApplication /*implements CommandLineRunner*/ {
//
//    @Autowired
//    private OperacaoRepository operacaoRepository;
//
//    @Autowired
//    private ClienteRepository clienteRepository;
//
//    @Autowired
//    private ContaRepository contaRepository;

    public static void main(String[] args) {
        SpringApplication.run(AgenciaBancariaApplication.class, args);
    }

    /*@Override
    public void run(String... args) throws Exception {

        // sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");


        Cliente cliente1 = new Cliente(null, "Carlos César", 11122, "10/18/1996");
        Cliente cliente2 = new Cliente(null, "João Augusto", 12345, "10/05/2017");

        clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));



        Conta conta1 = new Conta(null, "30/09/2017", 234.10 , cliente1);
        Conta conta2 = new Conta(null, "10/05/2017", 104.10 , cliente2);

        contaRepository.saveAll(Arrays.asList(conta1,conta2));



        Operacao op1 = new Operacao(null,1,2,100.00, "10/06/2017");
        Operacao op2 = new Operacao(null,2,1,300.00, "10/07/2017");

        operacaoRepository.saveAll(Arrays.asList(op1,op2));

    }*/
    }
