package com.bankbranch.service;

import java.util.List;

import com.bankbranch.domain.Account;
import com.bankbranch.domain.Operation;

public interface OperationService {

    Account findAccount();

    Operation typeOperation(Operation operation, Integer destinationAccount);

    List<Operation> findExtract();
}