package com.bankbranch.service;

import java.util.List;

import com.bankbranch.domain.Account;
import com.bankbranch.domain.Operation;

public interface OperationService {

    Account findAccount(Integer id);

    Operation typeOperation(Operation operation);

    List<Operation> findExtract(Integer id);
}