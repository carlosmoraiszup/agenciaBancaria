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
import com.bankbranch.dto.OperationDepositoDTO;
import com.bankbranch.repository.AccountRepository;
import com.bankbranch.repository.OperationRepository;
import com.bankbranch.service.OperationService;
import com.bankbranch.service.exception.EqualAccountTransfer;
import com.bankbranch.service.exception.InvalidAtributeException;
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
    public Account findAccount(Integer numberAccount) {
        Optional<Account> account = accountRepository.findByNumberAccount(numberAccount);

        if (!account.isPresent())
            throw new ObjectNotFoundException("Account not found!");

        return account.get();
    }

    @Override
    public Operation typeOperation(Integer originAccount, Operation operation, Integer destinationAccount) {

        operation.setDateOperation(LocalDateTime.now().format(formatter));
        if (operation.getValue() < 1)
            throw new InvalidAtributeException("Value must be greater than 0!");

        switch (operation.getOperationType()) {
            case DEPOSIT:
                Account accountDeposit = depositDate(operation, destinationAccount);
                accountDeposit = accountRepository.saveAndFlush(accountDeposit);
                Operation newOperation = new Operation(null, operation.getValue(), operation.getDateOperation(),
                        operation.getOperationType(), null, accountDeposit);
                operationRepository.saveAndFlush(newOperation);
                break;
            case WITHDRAW:
                Account accountWithdraw = withdrawDate(operation, originAccount);
                accountWithdraw = accountRepository.saveAndFlush(accountWithdraw);
                newOperation = new Operation(null, operation.getValue(), operation.getDateOperation(),
                        operation.getOperationType(), accountWithdraw, null);
                operationRepository.saveAndFlush(newOperation);
                break;
            case TRANSFER:
                if (originAccount == destinationAccount)
                    throw new EqualAccountTransfer("Prohibited transfer to same account!");

                accountDeposit = depositDate(operation, destinationAccount);
                accountWithdraw = withdrawDate(operation, originAccount);

                newOperation = new Operation(null, operation.getValue(), operation.getDateOperation(),
                        operation.getOperationType(), accountWithdraw, accountDeposit);
                operationRepository.saveAndFlush(newOperation);


                accountRepository.saveAndFlush(accountWithdraw);
                accountRepository.saveAndFlush(accountDeposit);

                break;

        }

        return operation;
    }


    private Account depositDate(Operation operation, Integer destinationAccount) {
        if (destinationAccount == null)
            throw new ObjectNotFoundException("Destination account should not be null!");
        Account account = findAccount(destinationAccount);
        operation.setNumberDestinationAccount(account);
        Double balance = account.getBalance() + operation.getValue();
        account.setBalance(balance);
        return account;
    }


    private Account withdrawDate(Operation operation, Integer originAccount) {
        if (originAccount == null)
            throw new ObjectNotFoundException("Origin account should not be null!");
        Account account = findAccount(originAccount);
        operation.setNumberOriginAccount(account);
        Double balance = account.getBalance() - operation.getValue();
        if (balance < 0)
            throw new UnprocessableEntityException("Unavailable balance!");
        account.setBalance(balance);
        return account;
    }

    @Override
    public List<Operation> findExtract(Integer id) {
        List<Operation> listOperation = operationRepository.searchExtract(id);
        for (Operation operation : listOperation) {
            if ((operation.getOperationType().equals(
                    OperationType.TRANSFER)) && null != (operation.getNumberOriginAccount())) {
                operation.setValue(operation.getValue() - (2 * operation.getValue()));
            }
        }
        return listOperation;
    }
}
