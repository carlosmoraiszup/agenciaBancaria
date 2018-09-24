package com.agenciaBancaria.domain;

import com.agenciaBancaria.domain.enums.TipoOperacao;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Operacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer idContaOrigem;
    private Integer idContaDestino;

    @Min(0)
    private Double  valor;

    private Integer tipoOperacao;

    private String dataOperacao;

    public Operacao(){}

    public Operacao(Integer id, Integer idContaOrigem, Integer idContaDestino, Double valor,
                    String dataOperacao, TipoOperacao tipoOperacao) {
        super();
        this.id = id;
        this.idContaOrigem = idContaOrigem;
        this.idContaDestino = idContaDestino;
        this.valor = valor;
        this.dataOperacao = dataOperacao;
        this.tipoOperacao = (tipoOperacao==null) ? null : tipoOperacao.getCod();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdContaOrigem() {
        return idContaOrigem;
    }

    public void setIdContaOrigem(Integer idContaOrigem) {
        this.idContaOrigem = idContaOrigem;
    }

    public Integer getIdContaDestino() {
        return idContaDestino;
    }

    public void setIdContaDestino(Integer idContaDestino) {
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
