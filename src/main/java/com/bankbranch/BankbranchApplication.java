package com.bankbranch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankbranchApplication /*implements CommandLineRunner*/ {
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
        SpringApplication.run(BankbranchApplication.class, args);
    }

    /*@Override
    public void run(String... args) throws Exception {

        // sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");


        Customer cliente1 = new Customer(null, "Carlos César", 11122, "10/18/1996");
        Customer cliente2 = new Customer(null, "João Augusto", 12345, "10/05/2017");

        clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));



        Account conta1 = new Account(null, "30/09/2017", 234.10 , cliente1);
        Account conta2 = new Account(null, "10/05/2017", 104.10 , cliente2);

        contaRepository.saveAll(Arrays.asList(conta1,conta2));



        Operation op1 = new Operation(null,1,2,100.00, "10/06/2017");
        Operation op2 = new Operation(null,2,1,300.00, "10/07/2017");

        operacaoRepository.saveAll(Arrays.asList(op1,op2));

    }*/
    }
