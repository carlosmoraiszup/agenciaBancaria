package com.agenciaBancaria.dto;

import java.io.Serializable;

import com.agenciaBancaria.domain.Operacao;
import com.agenciaBancaria.domain.enums.TipoOperacao;

public class OperacaoDepositoDTO{


    private Integer id;
    private Integer idContaDestino;
    private Double  valor;
    private String tipoOperacao;
    private String dataOperacao;

    public OperacaoDepositoDTO(){}

    public OperacaoDepositoDTO(Operacao operacao){
        this.id = operacao.getId();
        this.idContaDestino = operacao.getIdContaDestino();
        this.valor = operacao.getValor();
        this.tipoOperacao = TipoOperacao.toEnum(1).getDescricao();
        this.dataOperacao = operacao.getDataOperacao();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public String getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(String dataOperacao) {
        this.dataOperacao = dataOperacao;
    }




}
