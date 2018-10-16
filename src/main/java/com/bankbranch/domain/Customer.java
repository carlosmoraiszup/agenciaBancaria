package com.bankbranch.domain;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Columns;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "CPF should not be empty!")
    @NotNull(message = "CPF should not be null!")
    @Length(min = 11, max = 11, message = "CPF must have exactly 11 digits!")
    @CPF(message = "CPF is invalid")
    private String cpf;

    @NotEmpty(message = "Name should not be empty!")
    @Length(min = 3, message = "Name must be at least 3 characters!")
    @NotNull(message = "Name should not be null!")
    private String nameCustomer;

    private String dateCreation;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Customer() {
    }

    public Customer(Integer id, String nameCustomer, String cpf, Account id_account, String dateCreation) {
        super();
        this.id = id;
        this.nameCustomer = nameCustomer;
        this.cpf = cpf;
        this.account = id_account;
        this.dateCreation = dateCreation;

    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(cpf, customer.cpf) &&
                Objects.equals(nameCustomer, customer.nameCustomer) &&
                Objects.equals(dateCreation, customer.dateCreation) &&
                Objects.equals(account, customer.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf, nameCustomer, dateCreation, account);
    }
}
