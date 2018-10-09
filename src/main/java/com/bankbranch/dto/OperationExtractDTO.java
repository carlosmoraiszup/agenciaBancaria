package com.bankbranch.dto;

import com.bankbranch.domain.Operation;

public class OperationExtractDTO {


    private Integer id;
    private Double value;
    private Integer idDestinationAccount;
    private Integer idOriginAccount;
    private String dateOperation;
    private String typeOperation;

    public OperationExtractDTO() {
    }

    public OperationExtractDTO(Operation operation) {
        this.id = operation.getId();
        this.value = operation.getValue();
        if (null != operation.getIdOriginAccount()) {
            this.idOriginAccount = operation.getIdOriginAccount();
        }
        if (null != operation.getIdDestinationAccount()) {
            this.idDestinationAccount = operation.getIdDestinationAccount();
        }
        this.dateOperation = operation.getDateOperation();
        this.typeOperation = operation.getOperationType().getDescription();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdContaDestino() {
        return idDestinationAccount;
    }

    public void setIdContaDestino(Integer idContaDestino) {
        this.idDestinationAccount = idContaDestino;
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
