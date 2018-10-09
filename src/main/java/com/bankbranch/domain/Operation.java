package com.bankbranch.domain;

import com.bankbranch.domain.enums.OperationType;

import javax.persistence.*;
import javax.validation.constraints.Min;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Operation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idOriginAccount")
    private Account idOriginAccount;

    @ManyToOne
    @JoinColumn(name = "idDestinationAccount")
    private Account idDestinationAccount;

    @Min(1)
    private Double value;

    private Integer operationType;

    private String dateOperation;

    public Operation(){}

    public Operation(Integer id, Double value, String dateOperation, OperationType operationType, Account idOriginAccount,
            Account idDestinationAccount) {
        this.id = id;
        this.value = value;
        this.dateOperation = dateOperation;
        this.operationType = (operationType ==null) ? null : operationType.getCod();
        this.idOriginAccount = idOriginAccount;
        this.idDestinationAccount = idDestinationAccount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdOriginAccount() {
        if(idOriginAccount == null) {
            return null;
        }

        return idOriginAccount.getId();
    }

    public void setIdOriginAccount(Account idOriginAccount) {
        this.idOriginAccount = idOriginAccount;
    }

    public Integer getIdDestinationAccount() {
        if(idDestinationAccount == null) {
            return null;
        }

        return idDestinationAccount.getId();
    }

    public void setIdDestinationAccount(Account idDestinationAccount) {
        this.idDestinationAccount = idDestinationAccount;
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

    public OperationType getOperationType() {
        return OperationType.toEnum(operationType);
    }

    public void setOperationType(OperationType tipo) {
        this.operationType = tipo.getCod();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(id, operation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
