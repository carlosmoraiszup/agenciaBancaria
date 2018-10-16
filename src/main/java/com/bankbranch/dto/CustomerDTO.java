package com.bankbranch.dto;


import java.io.Serializable;

import com.bankbranch.domain.Account;
import com.bankbranch.domain.Customer;


public class CustomerDTO implements Serializable {


    private String cpf;

    private String nameCustomer;


    private Account account;

    public CustomerDTO(){}

    public CustomerDTO(Customer customer) {
        this.cpf = customer.getCpf();
        this.account = customer.getAccount();
        this.nameCustomer = customer.getNameCustomer();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

}
