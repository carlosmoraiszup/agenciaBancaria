package com.agenciaBancaria.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Operacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer id_Origem;
    private Integer id_Destino;
    private Double valor;

    private String dataOperacao;


    public Operacao(Integer id, Integer id_Origem, Integer id_Destino, Double valor, String dataOperacao) {
        super();
        this.id = id;
        this.id_Origem = id_Origem;
        this.id_Destino = id_Destino;
        this.valor = valor;
        this.dataOperacao = dataOperacao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_Origem() {
        return id_Origem;
    }

    public void setId_Origem(Integer id_Origem) {
        this.id_Origem = id_Origem;
    }

    public Integer getId_Destino() {
        return id_Destino;
    }

    public void setId_Destino(Integer id_Destino) {
        this.id_Destino = id_Destino;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(String dataOperacao) {
        this.dataOperacao = dataOperacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operacao operacao = (Operacao) o;
        return Objects.equals(id, operacao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
