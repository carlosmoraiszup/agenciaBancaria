package com.agenciaBancaria.domain.enums;

public enum TipoOperacao {
    DEPOSITO(1, "Depósito"),
    SAQUE(2, "Saque"),
    TRANSFERENCIA(3 , "Transferência");

    private int cod;
    private String descricao;

    private TipoOperacao(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }
    public int getCod() {
        return cod;
    }
    public String getDescricao() {
        return descricao;
    }
    public static TipoOperacao toEnum(Integer id) {

        if (id == null) {
            return null;
        }
        for (TipoOperacao x : TipoOperacao.values()) {
            if (id.equals(x.getCod())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Id inválido " + id);
    }
}