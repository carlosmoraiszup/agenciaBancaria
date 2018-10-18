package com.bankbranch.dto;

import com.bankbranch.domain.Operation;
import com.fasterxml.jackson.annotation.JsonInclude;

public class OperationExtractDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer numberAccount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double value;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer idDestinationAccount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer idOriginAccount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String dateOperation;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String typeOperation;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Double balanceAccount;

    public OperationExtractDTO() {
    }

    public OperationExtractDTO(Integer numberAccount, Double balance) {
        this.balanceAccount = balance;
        this.numberAccount = numberAccount;

    }

    public OperationExtractDTO(Operation operation) {
        this.value = operation.getValue();
        if (null != operation.getNumberOriginAccount()) {
            this.idOriginAccount = operation.getNumberOriginAccount();
        }
        if (null != operation.getNumberDestinationAccount()) {
            this.idDestinationAccount = operation.getNumberDestinationAccount();
        }
        this.dateOperation = operation.getDateOperation();
        this.typeOperation = operation.getOperationType().getDescription();
    }

    public Double getBalanceAccount() {
        return balanceAccount;
    }

    public void setBalanceAccount(Double balanceAccount) {
        this.balanceAccount = balanceAccount;
    }

    public Integer getIdDestinationAccount() {
        return idDestinationAccount;
    }

    public void setIdDestinationAccount(Integer idDestinationAccount) {
        this.idDestinationAccount = idDestinationAccount;
    }

    public Integer getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(Integer numberAccount) {
        this.numberAccount = numberAccount;
    }

    public Integer getIdOriginAccount() {
        return idOriginAccount;
    }

    public void setIdOriginAccount(Integer idOriginAccount) {
        this.idOriginAccount = idOriginAccount;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(String dateOperation) {
        this.dateOperation = dateOperation;
    }
}
