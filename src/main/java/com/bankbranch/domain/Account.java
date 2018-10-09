package com.bankbranch.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(0)
    private Double balance;

    private String dateCreation;

    @JsonIgnore
    @OneToMany(mappedBy = "idOriginAccount")
    private List<Operation> operationWithdraw = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "idDestinationAccount")
    private List<Operation> operationDeposit = new ArrayList<>();

    public Account() {
    }

    public Account(Integer id, String dateCreation, Double balance){
        this.id = id;
        this.dateCreation = dateCreation;
        this.balance = balance;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public List<Operation> getOperationWithdraw() {
        return operationWithdraw;
    }

    public void setOperationWithdraw(List<Operation> operationWithdraw) {
        this.operationWithdraw = operationWithdraw;
    }

    public List<Operation> getOperationDeposit() {
        return operationDeposit;
    }

    public void setOperationDeposit(List<Operation> operationDeposit) {
        this.operationDeposit = operationDeposit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
