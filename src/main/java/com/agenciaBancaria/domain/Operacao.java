package com.agenciaBancaria.domain;

import com.agenciaBancaria.domain.enums.TipoOperacao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Operacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer id_Origem;
    private Integer idDestino;
    private Double  valor;

    //@Enumerated(EnumType.STRING)
    private Integer tipoOperacao;

    private String dataOperacao;

    public Operacao(){}

    public Operacao(Integer id, Integer id_Origem, Integer idDestino, Double valor,
                    String dataOperacao, TipoOperacao tipoOperacao) {
        super();
        this.id = id;
        this.id_Origem = id_Origem;
        this.idDestino = idDestino;
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

    public Integer getId_Origem() {
        return id_Origem;
    }

    public void setId_Origem(Integer id_Origem) {
        this.id_Origem = id_Origem;
    }

    public Integer getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(Integer idDestino) {
        this.idDestino = idDestino;
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
