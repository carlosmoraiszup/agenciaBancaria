
package com.agenciaBancaria.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.agenciaBancaria.AbstractTest;
import com.bankbranch.domain.Account;
import com.bankbranch.domain.Customer;
import com.bankbranch.domain.Operation;
import com.bankbranch.domain.enums.OperationType;
import com.bankbranch.repository.AccountRepository;
import com.bankbranch.repository.OperationRepository;
import com.bankbranch.service.exception.EqualAccountTransfer;
import com.bankbranch.service.exception.ObjectNotFoundException;
import com.bankbranch.service.exception.UnprocessableEntityException;
import com.bankbranch.service.impl.OperationServiceImpl;


public class OperationServiceImplTest extends AbstractTest {


    @InjectMocks
    private OperationServiceImpl operacaoService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private OperationRepository operationRepository;

    private Account account1, account2;
    private Customer customer;
    private List<Operation> list = new ArrayList<>();
    private Operation operation;

    @Before
    public void setUP() {


        account1 = new Account(1, "01/01/2018", 0.0);

        account2 = new Account(2, "01/01/2018", 0.0);

        customer = new Customer(1, "Carlos", "11122233344", account1, "01/01/2018");

        operation = new Operation(2, 0.0, "23/02/2018" , null , null, null );

    }



    //findAccount
    @Test
    public void findAccountTestOK() {
        when(accountRepository.findById(anyInt())).thenReturn(Optional.of(account1));
        Account account = operacaoService.findAccount(1);
        assertNotNull(account);
    }

    @Test
    public void findAccountTestException(){
        when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        exception.expect(ObjectNotFoundException.class);
        Account account = operacaoService.findAccount(1);
    }


    //OperationType
    @Test
    public void typeOperationDepositoTestOK(){
        when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(account1));
        operation.setOperationType(OperationType.DEPOSIT);
        operation.setIdDestinationAccount(account1);
        Operation op = operacaoService.typeOperation(this.operation);
        verify((operationRepository), times(1)).saveAndFlush(any());
        verify(accountRepository, times(1)).saveAndFlush(any());
        assertNotNull(op);
    }

    @Test
    public void typeOperationDepositoTestExeption(){
        when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        exception.expect(ObjectNotFoundException.class);
        operation.setOperationType(OperationType.DEPOSIT);
        operation.setIdDestinationAccount(account1);
        operacaoService.typeOperation(operation);
    }


    @Test
    public void typeOperationSaqueTestOK(){
        when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(account1));
        operation.setOperationType(OperationType.WITHDRAW);
        operation.setIdOriginAccount(account1);
        Operation op = operacaoService.typeOperation(this.operation);
        verify((operationRepository), times(1)).saveAndFlush(any());
        verify(accountRepository, times(1)).saveAndFlush(any());
        assertNotNull(op);
    }

    @Test
    public void typeOperationSaqueTestExeption(){
        when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(account1));
        exception.expect(UnprocessableEntityException.class);
        operation.setOperationType(OperationType.WITHDRAW);
        operation.setIdOriginAccount(account1);
        operation.setValue(1.0);
        operacaoService.typeOperation(operation);
    }


    @Test
    public void typeOperationTransferenciaTestOK(){
        when(accountRepository.findById(1)).thenReturn(Optional.ofNullable(account1));
        when(accountRepository.findById(2)).thenReturn(Optional.ofNullable(account2));
        operation.setOperationType(OperationType.TRANSFER);
        operation.setIdOriginAccount(account1);
        operation.setIdDestinationAccount(account2);
        Operation op = operacaoService.typeOperation(this.operation);
        verify((operationRepository), times(2)).saveAndFlush(any());
        verify(accountRepository, times(2)).saveAndFlush(any());
        assertNotNull(op);
    }


    @Test
    public void typeOperationTransferenciaTestException(){
        when(accountRepository.findById(1)).thenReturn(Optional.ofNullable(account1));
        when(accountRepository.findById(2)).thenReturn(Optional.ofNullable(account2));
        exception.expect(EqualAccountTransfer.class);
        operation.setOperationType(OperationType.TRANSFER);
        operation.setIdOriginAccount(account1);
        operation.setIdDestinationAccount(account1);
        operacaoService.typeOperation(this.operation);
    }

    @Test
    public void findExtractTestOK() {
        list.add(operation);
        when(operationRepository.searchExtract(anyInt())).thenReturn(list);
        operation.setOperationType(OperationType.TRANSFER);
        operation.setIdOriginAccount(account1);
        operation.setValue(2.0);
        List<Operation> returnMethod = operacaoService.findExtract(1);
        assertFalse(returnMethod.isEmpty());
        assertEquals(Double.valueOf(-2.0), returnMethod.get(0).getValue());
    }

    @Test
    public void findExtractTestEmpty() {
        when(operationRepository.searchExtract(anyInt())).thenReturn(list);
        List<Operation> returnMethod = operacaoService.findExtract(1);
        assertTrue(returnMethod.isEmpty());
    }




}

