package com.bankbranch.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bankbranch.domain.Account;
import com.bankbranch.domain.Operation;
import com.bankbranch.domain.enums.OperationType;
import com.bankbranch.repository.AccountRepository;
import com.bankbranch.repository.OperationRepository;
import com.bankbranch.service.OperationService;
import com.bankbranch.service.exception.EqualAccountTransfer;
import com.bankbranch.service.exception.ObjectNotFoundException;
import com.bankbranch.service.exception.UnprocessableEntityException;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OperationServiceImpl implements OperationService {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OperationRepository operationRepository;

    @Override
    public Account findAccount(Integer id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElseThrow(() -> new ObjectNotFoundException("Account not found!"));
    }


    @Override
    public Operation typeOperation(Operation operation) {

        operation.setDateOperation(LocalDateTime.now().format(formatter));
        switch (operation.getOperationType()) {
            case DEPOSIT:
                Account accountDeposit = findAccount(operation.getIdDestinationAccount());
                depositDate(accountDeposit, operation);
                accountRepository.saveAndFlush(accountDeposit);
                break;
            case WITHDRAW:
                Account accountWithdraw = findAccount(operation.getIdOriginAccount());
                withdrawDate(accountWithdraw, operation);
                accountRepository.saveAndFlush(accountWithdraw);
                break;
            case TRANSFER:
                if (operation.getIdDestinationAccount() != operation.getIdOriginAccount()) {
                    accountWithdraw = findAccount(operation.getIdOriginAccount());
                    withdrawDate(accountWithdraw, operation);

                    accountDeposit = findAccount(operation.getIdDestinationAccount());
                    depositDate(accountDeposit, operation);

                    accountRepository.saveAndFlush(accountWithdraw);
                    accountRepository.saveAndFlush(accountDeposit);
                } else {
                    throw new EqualAccountTransfer("Prohibited transfer to same account!");
                }
                break;
        }

        return operation;
    }


    private void depositDate(Account account, Operation operation) {
        Double balance = account.getBalance() + operation.getValue();
        account.setBalance(balance);
        operationRepository.saveAndFlush(operation);

    }


    private void withdrawDate(Account account, Operation operation) {
        Double balance = account.getBalance() - operation.getValue();
        if (balance >= 0) {
            account.setBalance(balance);
            operationRepository.saveAndFlush(operation);
        } else {
            throw new UnprocessableEntityException("Unavailable balance!");
        }
    }

    @Override
    public List<Operation> findExtract(Integer id) {
        List<Operation> listOperation = operationRepository.searchExtract(id);
        for (Operation operation : listOperation) {
            if (operation.getOperationType().equals(OperationType.TRANSFER) && operation.getIdOriginAccount() == id) {
                operation.setValue(operation.getValue() - (2 * operation.getValue()));
            }
        }
        return listOperation;
    }
}
