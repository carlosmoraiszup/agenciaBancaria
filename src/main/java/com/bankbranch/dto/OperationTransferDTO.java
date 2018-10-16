package com.bankbranch.dto;

import com.bankbranch.domain.Operation;
import com.bankbranch.domain.enums.OperationType;

public class OperationTransferDTO {


    private Integer idOriginAccount;
    private Integer idDestinationAccount;
    private Double value;
    private String typeOperation;
    private String dateOperation;

    public OperationTransferDTO() {
    }

    public OperationTransferDTO(Operation operation) {
        this.idOriginAccount = operation.getNumberOriginAccount();
        this.idDestinationAccount = operation.getNumberDestinationAccount();
        this.value = operation.getValue();
        this.typeOperation = OperationType.toEnum(3).getDescription();
        this.dateOperation = operation.getDateOperation();
    }


    public Integer getIdOriginAccount() {
        return idOriginAccount;
    }

    public void setIdOriginAccount(Integer idOriginAccount) {
        this.idOriginAccount = idOriginAccount;
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
