package com.agenciaBancaria.dto;

public class ClienteDTO {


    private Integer id;
    private String cpf;
    private String nome;
    private String dataCriacao;

    public ClienteDTO(){}

    public ClienteDTO(Integer id, String cpf, String nome, String dataCriacao) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.dataCriacao = dataCriacao;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
