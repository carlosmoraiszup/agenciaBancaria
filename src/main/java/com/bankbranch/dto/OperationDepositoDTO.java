package com.bankbranch.dto;

import com.bankbranch.domain.Operation;
import com.bankbranch.domain.enums.OperationType;

public class OperationDepositoDTO {


    private Integer id;
    private Integer idDestinationAccount;
    private Double value;
    private String typeOperation;
    private String dateOperation;

    public OperationDepositoDTO(){}

    public OperationDepositoDTO(Operation operation){
        this.id = operation.getId();
        this.idDestinationAccount = operation.getIdDestinationAccount();
        this.value = operation.getValue();
        this.typeOperation = OperationType.toEnum(1).getDescription();
        this.dateOperation = operation.getDateOperation();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdDestinationAccount() {
        return idDestinationAccount;
    }

    public void setIdDestinationAccount(Integer idDestinationAccount) {
        this.idDestinationAccount = idDestinationAccount;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public String getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(String dateOperation) {
        this.dateOperation = dateOperation;
    }




}
