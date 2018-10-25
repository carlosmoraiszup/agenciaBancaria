package com.bankbranch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankbranchApplication implements CommandLineRunner/*implements CommandLineRunner*/ {

    public static void main(String[] args) {

        SpringApplication.run(BankbranchApplication.class, args);


    }

    @Override
    public void run(String... args) throws Exception {
    }

}
