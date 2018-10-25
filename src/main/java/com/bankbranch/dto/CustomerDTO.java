package com.bankbranch.dto;


import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.bankbranch.domain.Account;
import com.bankbranch.domain.Customer;
import com.bankbranch.domain.enums.Profile;


public class CustomerDTO implements Serializable {


    private String cpf;

    private String nameCustomer;

    @NotEmpty
    private String password;

    private Account account;

    private Set<Profile> perfis;

    public CustomerDTO() {
    }

    public CustomerDTO(Customer customer) {
        this.cpf = customer.getCpf();
        this.account = customer.getAccount();
        this.nameCustomer = customer.getNameCustomer();
        this.password = customer.getPassword();
        this.perfis = customer.getProfile();
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
