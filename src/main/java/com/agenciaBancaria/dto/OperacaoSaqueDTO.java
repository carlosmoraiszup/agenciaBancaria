package com.agenciaBancaria.dto;

import com.agenciaBancaria.domain.Operacao;
import com.agenciaBancaria.domain.enums.TipoOperacao;

public class OperacaoSaqueDTO{


    private Integer id;
    private Integer idContaOrigem;
    private Double  valor;
    private String tipoOperacao;
    private String dataOperacao;

    public OperacaoSaqueDTO(){}

    public OperacaoSaqueDTO(Operacao operacao){
        this.id = operacao.getId();
        this.idContaOrigem = operacao.getIdContaOrigem();
        this.valor = operacao.getValor();
        this.tipoOperacao = TipoOperacao.toEnum(2).getDescricao();
        this.dataOperacao = operacao.getDataOperacao();
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
