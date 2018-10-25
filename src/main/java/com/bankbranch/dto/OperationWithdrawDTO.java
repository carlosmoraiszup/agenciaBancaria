package com.bankbranch.dto;

import com.bankbranch.domain.Operation;
import com.bankbranch.domain.enums.OperationType;

public class OperationWithdrawDTO {

    private Double value;
    private String typeOperation;
    private String dateOperation;

    public OperationWithdrawDTO() {
    }

    public OperationWithdrawDTO(Operation operation) {
        this.value = operation.getValue();
        this.typeOperation = OperationType.toEnum(2).getDescription();
        this.dateOperation = operation.getDateOperation();
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
