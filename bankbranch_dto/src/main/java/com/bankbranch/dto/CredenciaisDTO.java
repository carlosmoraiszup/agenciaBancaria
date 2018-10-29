package com.bankbranch.dto;

public class CredenciaisDTO {
    private String cpf;
    private String password;

    public CredenciaisDTO(){}

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
