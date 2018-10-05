package com.agenciaBancaria.domain;

import com.agenciaBancaria.domain.enums.TipoOperacao;
import com.agenciaBancaria.dto.OperacaoDepositoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.lang.Nullable;

@Entity
public class Operacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idContaOrigem")
    private Conta idContaOrigem;

    @ManyToOne
    @JoinColumn(name = "idContaDestino")
    private Conta idContaDestino;

    @Min(1)
    private Double  valor;

    private Integer tipoOperacao;

    private String dataOperacao;

    public Operacao(){}

    public Operacao(Integer id, Double valor, String dataOperacao, TipoOperacao tipoOperacao, Conta idContaOrigem,
            Conta idContaDestino) {
        this.id = id;
        this.valor = valor;
        this.dataOperacao = dataOperacao;
        this.tipoOperacao = (tipoOperacao==null) ? null : tipoOperacao.getCod();
        this.idContaOrigem = idContaOrigem;
        this.idContaDestino = idContaDestino;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdContaOrigem() {
        if(idContaOrigem == null) {
            return null;
        }

        return idContaOrigem.getId();
    }

    public void setIdContaOrigem(Conta idContaOrigem) {
        this.idContaOrigem = idContaOrigem;
    }

    public Integer getIdContaDestino() {
        if(idContaDestino == null) {
            return null;
        }

        return idContaDestino.getId();
    }

    public void setIdContaDestino(Conta idContaDestino) {
        this.idContaDestino = idContaDestino;
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

    public TipoOperacao getTipoOperacao() {
        return TipoOperacao.toEnum(tipoOperacao);
    }

    public void setTipoOperacao(TipoOperacao tipo) {
        this.tipoOperacao = tipo.getCod();
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
