package com.bankbranch.domain.enums;

public enum Perfil {
    ADMIN(1, "ROLE_ADMIN"),
    CUSTOMER(2, "ROLE_CUSTOMER");

    private int cod;
    private String description;

    private Perfil(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }
    public int getCod() {
        return cod;
    }
    public String getDescription() {
        return description;
    }
    public static Perfil toEnum(Integer id) {

        if (id == null) {
            return null;
        }
        for (Perfil x : Perfil.values()) {
            if (id.equals(x.getCod())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Invalid ID " + id);
    }
}