package com.agenciaBancaria.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Conta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String dataCriacao;

    private Double saldo;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Conta(){}

    public Conta(Integer id, String dataCriacao, Double saldo, Cliente id_cliente) {
        super();
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.saldo = saldo;
        this.cliente = id_cliente;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return Objects.equals(id, conta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
