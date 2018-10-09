package com.bankbranch.dto;

public class CustomerDTO {


    private Integer id;
    private String cpf;
    private String nameCustomer;
    private String dateCreated;

    public CustomerDTO(){}

    public CustomerDTO(Integer id, String cpf, String nameCustomer, String dateCreated) {
        this.id = id;
        this.cpf = cpf;
        this.nameCustomer = nameCustomer;
        this.dateCreated = dateCreated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
