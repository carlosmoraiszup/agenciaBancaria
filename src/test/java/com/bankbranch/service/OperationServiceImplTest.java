package com.bankbranch.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.bankbranch.AbstractTest;
import com.bankbranch.domain.Account;
import com.bankbranch.domain.Customer;
import com.bankbranch.domain.Operation;
import com.bankbranch.domain.enums.OperationType;
import com.bankbranch.domain.enums.Profile;
import com.bankbranch.repository.AccountRepository;
import com.bankbranch.repository.CustomerRepository;
import com.bankbranch.repository.OperationRepository;
import com.bankbranch.security.UserSS;
import com.bankbranch.service.exception.AuthorizationException;
import com.bankbranch.service.exception.EqualAccountTransfer;
import com.bankbranch.service.exception.InvalidAtributeException;
import com.bankbranch.service.exception.ObjectNotFoundException;
import com.bankbranch.service.exception.UnprocessableEntityException;
import com.bankbranch.service.impl.OperationServiceImpl;
import com.bankbranch.service.impl.UserServiceImpl;


public class OperationServiceImplTest extends AbstractTest {


    @InjectMocks
    private OperationServiceImpl operacaoService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private OperationRepository operationRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private UserServiceImpl userServiceImpl;

    private Account account1, account2;
    private Customer customer;
    private List<Operation> list = new ArrayList<>();
    private Operation operation;
    private Set<Profile> profiles = new HashSet<>();

    @Before
    public void setUP() {
        profiles.add(Profile.CUSTOMER);
        account1 = new Account(1, "01/01/2018", 4.0);
        account2 = new Account(2, "01/01/2018", 5.0);
        customer = new Customer(1, "Carlos", "10575823607", account1, "01/01/2018", "12");
        operation = new Operation(2, 2.0, "23/02/2018", null, null, null);
    }


    //findAccount
    @Test
    public void findAccount_WhenUserIsAuthenticatedReturnsYourAccount() {
        UserSS user = new UserSS(null, "10575823607", null, profiles);
        when(userServiceImpl.authenticated()).thenReturn(user);
        when(customerRepository.findByCpf("10575823607")).thenReturn(customer);
        when(accountRepository.findByNumberAccount(anyInt())).thenReturn(Optional.of(account1));
        Account account = operacaoService.findAccount();
        assertNotNull(account);
    }

    @Test
    public void findAccount_IfUserIsNullReturnsThrowException() {
        when(userServiceImpl.authenticated()).thenReturn(null);
        exception.expect(ObjectNotFoundException.class);
        exception.expectMessage("User not found!");
        operacaoService.findAccount();
    }

    @Test
    public void findAccount_IfCustomerIsNullReturnsThrowException() {
        UserSS user = new UserSS(null, "10575823607", null, profiles);
        when(userServiceImpl.authenticated()).thenReturn(user);
        when(customerRepository.findByCpf("10575823607")).thenReturn(null);
        exception.expect(ObjectNotFoundException.class);
        exception.expectMessage("Customer not found!");
        operacaoService.findAccount();
    }

    @Test
    public void findAccount_WhenTheProfileDiffersFromAdminAndCpfFromTheClientOtherThanUsernameThrowException() {
        UserSS user = new UserSS(null, "96300388034", null, profiles);
        when(userServiceImpl.authenticated()).thenReturn(user);
        when(customerRepository.findByCpf(user.getUsername())).thenReturn(customer);
        exception.expect(AuthorizationException.class);
        exception.expectMessage("Access denied!");
        operacaoService.findAccount();
    }


    //OperationType

    @Test
    public void typeOperationTestValueException() {
        operation.setValue(0.0);
        exception.expect(InvalidAtributeException.class);
        operacaoService.typeOperation(operation, 1111);
    }

    @Test
    public void typeOperation_WhenOperationIsDepositReturnOperation() {
        when(accountRepository.findByNumberAccount(anyInt())).thenReturn(Optional.of(account1));
        operation.setOperationType(OperationType.DEPOSIT);
        Operation op = operacaoService.typeOperation(operation, account1.getNumberAccount());
        verify((operationRepository), times(1)).saveAndFlush(any());
        verify(accountRepository, times(1)).saveAndFlush(any());
        assertNotNull(op);
    }

    @Test
    public void typeOperation_WhenOperationIsDepositAndCanNotFindAnAccountThrowException() {
        when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        exception.expect(ObjectNotFoundException.class);
        operation.setOperationType(OperationType.DEPOSIT);
        operation.setDestinationAccount(account1);
        operacaoService.typeOperation(operation, null);
    }

    @Test
    public void typeOperation_WhenOperationIsDepositAndAccounIsNullThrowException() {
        when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(account1));
        exception.expect(ObjectNotFoundException.class);
        operation.setOperationType(OperationType.DEPOSIT);
        operation.setDestinationAccount(account1);
        operacaoService.typeOperation(operation, null);
    }

    @Test
    public void typeOperation_WhenTheTransactionIsDepositAndCanNotFindAnAccountThenThrowException() {
        when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(account1));
        when(accountRepository.findByNumberAccount(1001)).thenReturn(Optional.empty());
        exception.expect(ObjectNotFoundException.class);
        operation.setOperationType(OperationType.DEPOSIT);
        operation.setDestinationAccount(account1);
        operacaoService.typeOperation(operation, 1001);
        verify(accountRepository, times(1)).saveAndFlush(any());

    }

    //OperationType
    @Test
    public void typeOperation_WhenOperationIsWithdrawReturnOperation() {
        UserSS user = new UserSS(null, "10575823607", null, profiles);
        when(userServiceImpl.authenticated()).thenReturn(user);
        when(customerRepository.findByCpf(user.getUsername())).thenReturn(customer);
        when(accountRepository.findByNumberAccount(anyInt())).thenReturn(Optional.of(account1));
        operation.setOperationType(OperationType.WITHDRAW);
        Operation op = operacaoService.typeOperation(operation, null);
        verify((operationRepository), times(1)).saveAndFlush(any());
        verify(accountRepository, times(1)).saveAndFlush(any());
        assertNotNull(op);
    }


    @Test
    public void typeOperation_WhenOperationIsWithdrawUnavailableBalanceThrowException() {
        account1.setBalance(1.0);
        UserSS user = new UserSS(null, "10575823607", null, profiles);
        when(userServiceImpl.authenticated()).thenReturn(user);
        when(customerRepository.findByCpf(user.getUsername())).thenReturn(customer);
        when(accountRepository.findByNumberAccount(anyInt())).thenReturn(Optional.ofNullable(account1));
        exception.expect(UnprocessableEntityException.class);
        operation.setOperationType(OperationType.WITHDRAW);
        operacaoService.typeOperation(operation, null);
    }


    @Test
    public void typeOperation_WhenOperationIsTransferMoneyAndTheAccountsAreDifferentReturnsOperation() {
        UserSS user = new UserSS(null, "10575823607", null, profiles);
        when(userServiceImpl.authenticated()).thenReturn(user);
        when(customerRepository.findByCpf(user.getUsername())).thenReturn(customer);
        when(accountRepository.findByNumberAccount(1)).thenReturn(Optional.ofNullable(account1));
        when(accountRepository.findByNumberAccount(2)).thenReturn(Optional.ofNullable(account2));
        operation.setOperationType(OperationType.TRANSFER);
        Operation op = operacaoService.typeOperation(operation,
                account2.getNumberAccount());
        verify((operationRepository), times(1)).saveAndFlush(any());
        verify(accountRepository, times(2)).saveAndFlush(any());
        assertNotNull(op);
    }


    @Test
    public void typeOperationand_WhenOperationIsTransferMoneyAndTheAccountsAreTheSameThen() {
        UserSS user = new UserSS(null, "10575823607", null, profiles);
        when(userServiceImpl.authenticated()).thenReturn(user);
        when(customerRepository.findByCpf(user.getUsername())).thenReturn(customer);
        when(accountRepository.findByNumberAccount(1)).thenReturn(Optional.ofNullable(account1));
        exception.expect(EqualAccountTransfer.class);
        operation.setOperationType(OperationType.TRANSFER);
        operacaoService.typeOperation(operation, account1.getNumberAccount());
    }

    @Test
    public void findExtract_IfOperationOfThisCustomesReturnsInAList() {
        list.add(operation);
        UserSS user = new UserSS(null, "10575823607", null, profiles);
        when(userServiceImpl.authenticated()).thenReturn(user);
        when(customerRepository.findByCpf(user.getUsername())).thenReturn(customer);
        when(operationRepository.searchExtract(anyInt())).thenReturn(list);
        operation.setOperationType(OperationType.TRANSFER);
        operation.setOriginAccount(account1);
        operation.setValue(2.0);
        List<Operation> returnMethod = operacaoService.findExtract();
        assertFalse(returnMethod.isEmpty());
        assertEquals(Double.valueOf(-2.0), returnMethod.get(0).getValue());
    }

    @Test
    public void findExtract_IfNoOperationOfThisCustomerReturnsAnEmptyList() {
        UserSS user = new UserSS(null, "10575823607", null, profiles);
        when(userServiceImpl.authenticated()).thenReturn(user);
        when(customerRepository.findByCpf(user.getUsername())).thenReturn(customer);
        when(operationRepository.searchExtract(anyInt())).thenReturn(list);
        List<Operation> returnMethod = operacaoService.findExtract();
        assertTrue(returnMethod.isEmpty());
    }


}

