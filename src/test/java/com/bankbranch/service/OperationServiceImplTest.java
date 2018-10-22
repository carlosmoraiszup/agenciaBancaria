//package com.bankbranch.service;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import com.bankbranch.AbstractTest;
//import com.bankbranch.domain.Account;
//import com.bankbranch.domain.Customer;
//import com.bankbranch.domain.Operation;
//import com.bankbranch.domain.enums.OperationType;
//import com.bankbranch.repository.AccountRepository;
//import com.bankbranch.repository.OperationRepository;
//import com.bankbranch.service.exception.EqualAccountTransfer;
//import com.bankbranch.service.exception.InvalidAtributeException;
//import com.bankbranch.service.exception.ObjectNotFoundException;
//import com.bankbranch.service.exception.UnprocessableEntityException;
//import com.bankbranch.service.impl.OperationServiceImpl;
//
//
//public class OperationServiceImplTest extends AbstractTest {
//
//
//    @InjectMocks
//    private OperationServiceImpl operacaoService;
//
//    @Mock
//    private AccountRepository accountRepository;
//
//    @Mock
//    private OperationRepository operationRepository;
//
//    private Account account1, account2;
//    private Customer customer;
//    private List<Operation> list = new ArrayList<>();
//    private Operation operation;
//
//    @Before
//    public void setUP() {
//
//
//        account1 = new Account(1, "01/01/2018", 4.0);
//
//        account2 = new Account(2, "01/01/2018", 5.0);
//
//        customer = new Customer(1, "Carlos", "11122233344", account1, "01/01/2018", "12");
//
//        operation = new Operation(2, 2.0, "23/02/2018", null, null, null);
//
//    }
//
//
//    //findAccount
//    @Test
//    public void findAccountTestOK() {
//        when(accountRepository.findByNumberAccount(anyInt())).thenReturn(Optional.of(account1));
//        Account account = operacaoService.findAccount(1);
//        assertNotNull(account);
//    }
//
//    @Test
//    public void findAccountTestException() {
//        Optional<Account> account = Optional.empty();
//        when(accountRepository.findByNumberAccount(anyInt())).thenReturn(account);
//        exception.expect(ObjectNotFoundException.class);
//        operacaoService.findAccount(1);
//    }
//
//
//    //OperationType
//
//    @Test
//    public void typeOperationTestValueException() {
//        operation.setValue(0.0);
//        exception.expect(InvalidAtributeException.class);
//        operacaoService.typeOperation(null, operation, null);
//    }
//
//    @Test
//    public void typeOperationDepositTestOK() {
//        when(accountRepository.findByNumberAccount(anyInt())).thenReturn(Optional.of(account1));
//        operation.setOperationType(OperationType.DEPOSIT);
//        Operation op = operacaoService.typeOperation(null, operation, account1.getNumberAccount());
//        verify((operationRepository), times(1)).saveAndFlush(any());
//        verify(accountRepository, times(1)).saveAndFlush(any());
//        assertNotNull(op);
//    }
//
//    @Test
//    public void typeOperationDepositTestException() {
//        when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
//        exception.expect(ObjectNotFoundException.class);
//        operation.setOperationType(OperationType.DEPOSIT);
//        operation.setNumberDestinationAccount(account1);
//        operacaoService.typeOperation(null, operation, null);
//    }
//
//    //OperationType
//    @Test
//    public void typeOperationWithdrawTestOK() {
//        when(accountRepository.findByNumberAccount(anyInt())).thenReturn(Optional.of(account1));
//        operation.setOperationType(OperationType.WITHDRAW);
//        Operation op = operacaoService.typeOperation(account1.getNumberAccount(), operation, null);
//        verify((operationRepository), times(1)).saveAndFlush(any());
//        verify(accountRepository, times(1)).saveAndFlush(any());
//        assertNotNull(op);
//    }
//
//    @Test
//    public void typeOperationWithdrawTestExceptionAccount() {
//        when(accountRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
//        exception.expect(ObjectNotFoundException.class);
//        operation.setOperationType(OperationType.WITHDRAW);
//        operacaoService.typeOperation(null, operation, null);
//    }
//
//    @Test
//    public void typeOperationWithdrawTestUnavailableBalanceException() {
//        account1.setBalance(1.0);
//        when(accountRepository.findByNumberAccount(anyInt())).thenReturn(Optional.ofNullable(account1));
//        exception.expect(UnprocessableEntityException.class);
//        operation.setOperationType(OperationType.WITHDRAW);
//        operacaoService.typeOperation(account1.getNumberAccount(), operation, null);
//    }
//
//
//    @Test
//    public void typeOperationTransferMoneyTestOK() {
//        when(accountRepository.findByNumberAccount(1)).thenReturn(Optional.ofNullable(account1));
//        when(accountRepository.findByNumberAccount(2)).thenReturn(Optional.ofNullable(account2));
//        operation.setOperationType(OperationType.TRANSFER);
//        Operation op = operacaoService.typeOperation(account1.getNumberAccount(), operation,
//                account2.getNumberAccount());
//        verify((operationRepository), times(1)).saveAndFlush(any());
//        verify(accountRepository, times(2)).saveAndFlush(any());
//        assertNotNull(op);
//    }
//
//
//    @Test
//    public void typeOperationTransferMoneyTestException() {
//        when(accountRepository.findByNumberAccount(1)).thenReturn(Optional.ofNullable(account1));
//        exception.expect(EqualAccountTransfer.class);
//        operation.setOperationType(OperationType.TRANSFER);
//        operacaoService.typeOperation(account1.getNumberAccount(), operation, account1.getNumberAccount());
//    }
//
//    @Test
//    public void typeOperationTransferMoneyTestOriginIsNullException() {
//        when(accountRepository.findByNumberAccount(1)).thenReturn(Optional.ofNullable(account1));
//        exception.expect(ObjectNotFoundException.class);
//        operation.setOperationType(OperationType.TRANSFER);
//        operacaoService.typeOperation(null, operation, account1.getNumberAccount());
//    }
//
//    @Test
//    public void typeOperationTransferMoneyTestDestinationIsNullException() {
//        when(accountRepository.findByNumberAccount(1)).thenReturn(Optional.ofNullable(account1));
//        exception.expect(ObjectNotFoundException.class);
//        operation.setOperationType(OperationType.TRANSFER);
//        operacaoService.typeOperation(account1.getNumberAccount(), operation, null);
//    }
//
//    @Test
//    public void findExtractTestOK() {
//        list.add(operation);
//        when(operationRepository.searchExtract(anyInt())).thenReturn(list);
//        operation.setOperationType(OperationType.TRANSFER);
//        operation.setNumberOriginAccount(account1);
//        operation.setValue(2.0);
//        List<Operation> returnMethod = operacaoService.findExtract(1);
//        assertFalse(returnMethod.isEmpty());
//        assertEquals(Double.valueOf(-2.0), returnMethod.get(0).getValue());
//    }
//
//    @Test
//    public void findExtractTestEmpty() {
//        when(operationRepository.searchExtract(anyInt())).thenReturn(list);
//        List<Operation> returnMethod = operacaoService.findExtract(1);
//        assertTrue(returnMethod.isEmpty());
//    }
//
//
//}
//
