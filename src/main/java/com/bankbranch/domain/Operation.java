package com.bankbranch.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

import com.bankbranch.domain.enums.OperationType;

@Entity
public class Operation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "originAccount")
    private Account originAccount;

    @ManyToOne
    @JoinColumn(name = "destinationAccount")
    private Account destinationAccount;

    @Min(1)
    private Double value;

    private Integer operationType;

    private String dateOperation;

    public Operation() {
    }

    public Operation(Integer id, Double value, String dateOperation, OperationType operationType,
            Account originAccount,
            Account destinationAccount) {
        this.id = id;
        this.value = value;
        this.dateOperation = dateOperation;
        this.operationType = (operationType == null) ? null : operationType.getCod();
        this.originAccount = originAccount;
        this.destinationAccount = destinationAccount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOriginAccount() {
        if (originAccount == null) {
            return null;
        }

        return originAccount.getNumberAccount();
    }

    public void setOriginAccount(Account originAccount) {
        this.originAccount = originAccount;
    }

    public Integer getDestinationAccount() {
        if (destinationAccount == null) {
            return null;
        }

        return destinationAccount.getNumberAccount();
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
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
