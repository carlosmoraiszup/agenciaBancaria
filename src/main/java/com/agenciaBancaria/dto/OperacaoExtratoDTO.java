package com.agenciaBancaria.dto;

import com.agenciaBancaria.domain.Operacao;

public class OperacaoExtratoDTO {


    private Integer id;
    private Double  valor;
    private Integer idContaDestino;
    private Integer idContaOrigem;
    private String dataOperacao;
    private String tipoOpecao;

    public OperacaoExtratoDTO(){}

    public OperacaoExtratoDTO(Operacao operacao){
        this.id = operacao.getId();
        this.valor = operacao.getValor();
        if(null != operacao.getIdContaOrigem()){
            this.idContaOrigem = operacao.getIdContaOrigem();
        }
        if(null != operacao.getIdContaDestino()) {
            this.idContaDestino = operacao.getIdContaDestino();
        }
        this.dataOperacao = operacao.getDataOperacao();
        this.tipoOpecao = operacao.getTipoOperacao().getDescricao();
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

    public Integer getIdContaOrigem() {
        return idContaOrigem;
    }

    public void setIdContaOrigem(Integer idContaOrigem) {
        this.idContaOrigem = idContaOrigem;
    }

    public String getTipoOpecao() {
        return tipoOpecao;
    }

    public void setTipoOpecao(String tipoOpecao) {
        this.tipoOpecao = tipoOpecao;
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
}
