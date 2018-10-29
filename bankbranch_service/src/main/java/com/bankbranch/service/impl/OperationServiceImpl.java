package com.bankbranch.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bankbranch.authorizations.UserSS;
import com.bankbranch.domain.Account;
import com.bankbranch.domain.Customer;
import com.bankbranch.domain.Operation;
import com.bankbranch.domain.enums.OperationType;
import com.bankbranch.domain.enums.Profile;
import com.bankbranch.repository.AccountRepository;
import com.bankbranch.repository.CustomerRepository;
import com.bankbranch.repository.OperationRepository;
import com.bankbranch.service.OperationService;
import com.bankbranch.service.exception.AuthorizationException;
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
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public Account findAccount() {
        UserSS user = userServiceImpl.authenticated();
        if (null == user)
            throw new ObjectNotFoundException("User not found!");

        Customer customer = customerRepository.findByCpf(user.getUsername());
        if (null == customer)
            throw new ObjectNotFoundException("Customer not found!");

        if (!user.hasRole(Profile.ADMIN) && !customer.getCpf().equals(user.getUsername()))
            throw new AuthorizationException("Access denied!");

        Optional<Account> account = accountRepository.findByNumberAccount(customer.getAccount().getNumberAccount());

        if (!account.isPresent())
            throw new ObjectNotFoundException("Account not found!");

        return account.get();
    }

    @Override
    public Operation typeOperation(Operation operation, Integer destinationAccount) {
        UserSS user;
        Customer customer = null;

        operation.setDateOperation(LocalDateTime.now().format(formatter));
        if (operation.getValue() < 1)
            throw new InvalidAtributeException("Value must be greater than 0!");

        if (!operation.getOperationType().equals(OperationType.DEPOSIT)) {
            user = userServiceImpl.authenticated();
            customer = customerRepository.findByCpf(user.getUsername());
        }

        switch (operation.getOperationType()) {
            case DEPOSIT:
                Account accountDeposit = depositDate(operation, destinationAccount);
                accountDeposit = accountRepository.saveAndFlush(accountDeposit);
                Operation newOperation = new Operation(null, operation.getValue(), operation.getDateOperation(),
                        operation.getOperationType(), null, accountDeposit);
                operationRepository.saveAndFlush(newOperation);
                break;
            case WITHDRAW:
                Account accountWithdraw = withdrawDate(operation, customer);
                accountWithdraw = accountRepository.saveAndFlush(accountWithdraw);
                newOperation = new Operation(null, operation.getValue(), operation.getDateOperation(),
                        operation.getOperationType(), accountWithdraw, null);
                operationRepository.saveAndFlush(newOperation);
                break;
            case TRANSFER:
                if (customer.getAccount().getNumberAccount().intValue() == destinationAccount)
                    throw new EqualAccountTransfer("Prohibited transfer to same account!");

                accountDeposit = depositDate(operation, destinationAccount);
                accountWithdraw = withdrawDate(operation, customer);

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

        Optional<Account> account = accountRepository.findByNumberAccount(destinationAccount);
        if (!account.isPresent())
            throw new ObjectNotFoundException("Account not found!");

        operation.setDestinationAccount(account.get());
        Double balance = account.get().getBalance() + operation.getValue();
        account.get().setBalance(balance);
        return account.get();
    }


    private Account withdrawDate(Operation operation, Customer customer) {
        operation.setOriginAccount(customer.getAccount());
        Double balance = customer.getAccount().getBalance() - operation.getValue();
        if (balance < 0)
            throw new UnprocessableEntityException("Unavailable balance!");
        customer.getAccount().setBalance(balance);
        return customer.getAccount();
    }

    @Override
    public List<Operation> findExtract() {
        UserSS user = userServiceImpl.authenticated();
        Customer customer = customerRepository.findByCpf(user.getUsername());

        List<Operation> listOperation = operationRepository.searchExtract(customer.getAccount().getNumberAccount());
        for (Operation operation : listOperation) {
            if ((operation.getOperationType().equals(
                    OperationType.TRANSFER)) && null != (operation.getOriginAccount())) {
                operation.setValue(operation.getValue() - (2 * operation.getValue()));
            }
        }
        return listOperation;
    }
}
