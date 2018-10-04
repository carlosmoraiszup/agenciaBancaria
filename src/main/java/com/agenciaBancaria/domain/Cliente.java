package com.agenciaBancaria.domain;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@Entity
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Preenchimeneto obrigatorio!")
    @Length(min = 11, max = 11, message = "Deve haver exatamente 11 digitos")
    @Column(unique = true)
    private String cpf;

    @NotEmpty(message = "Preenchimeneto obrigatorio!")
    private String nome;

    private String dataCriacao;

    @OneToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    public Cliente() {
    }

    public Cliente(Integer id, String nome, String cpf, Conta id_conta, String dataCriacao) {
        super();
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.conta = id_conta;
        this.dataCriacao = dataCriacao;

    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) &&
                Objects.equals(cpf, cliente.cpf) &&
                Objects.equals(nome, cliente.nome) &&
                Objects.equals(dataCriacao, cliente.dataCriacao) &&
                Objects.equals(conta, cliente.conta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf, nome, dataCriacao, conta);
    }
}
