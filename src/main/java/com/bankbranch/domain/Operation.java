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
    @JoinColumn(name = "numberOriginAccount")
    private Account numberOriginAccount;

    @ManyToOne
    @JoinColumn(name = "numberDestinationAccount")
    private Account numberDestinationAccount;

    @Min(1)
    private Double value;

    private Integer operationType;

    private String dateOperation;

    public Operation(){}

    public Operation(Integer id, Double value, String dateOperation, OperationType operationType, Account numberOriginAccount,
            Account numberDestinationAccount) {
        this.id = id;
        this.value = value;
        this.dateOperation = dateOperation;
        this.operationType = (operationType ==null) ? null : operationType.getCod();
        this.numberOriginAccount = numberOriginAccount;
        this.numberDestinationAccount = numberDestinationAccount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberOriginAccount() {
        if(numberOriginAccount == null) {
            return null;
        }

        return numberOriginAccount.getId();
    }

    public void setNumberOriginAccount(Account numberOriginAccount) {
        this.numberOriginAccount = numberOriginAccount;
    }

    public Integer getNumberDestinationAccount() {
        if(numberDestinationAccount == null) {
            return null;
        }

        return numberDestinationAccount.getId();
    }

    public void setNumberDestinationAccount(Account numberDestinationAccount) {
        this.numberDestinationAccount = numberDestinationAccount;
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
