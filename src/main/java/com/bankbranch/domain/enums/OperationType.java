package com.bankbranch.domain.enums;

public enum OperationType {
    DEPOSIT(1, "DEPOSIT"),
    WITHDRAW(2, "WITHDRAW"),
    TRANSFER(3 , "TRANSFER");

    private int cod;
    private String description;

    private OperationType(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }
    public int getCod() {
        return cod;
    }
    public String getDescription() {
        return description;
    }
    public static OperationType toEnum(Integer id) {

        if (id == null) {
            return null;
        }
        for (OperationType x : OperationType.values()) {
            if (id.equals(x.getCod())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Invalid ID " + id);
    }
}