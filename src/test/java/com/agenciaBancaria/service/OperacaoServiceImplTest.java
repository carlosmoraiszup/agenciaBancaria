
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.agenciaBancaria.AbstractTest;
import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.domain.Operacao;
import com.agenciaBancaria.domain.enums.TipoOperacao;
import com.agenciaBancaria.repository.ClienteRepository;
import com.agenciaBancaria.repository.ContaRepository;
import com.agenciaBancaria.repository.OperacaoRepository;
import com.agenciaBancaria.service.exception.EqualAccountTransfer;
import com.agenciaBancaria.service.exception.ObjectNotFoundException;
import com.agenciaBancaria.service.exception.UnprocessableEntity;
import com.agenciaBancaria.service.impl.ClienteServiceImpl;
import com.agenciaBancaria.service.impl.OperacaoServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


public class OperacaoServiceImplTest extends AbstractTest {


    @InjectMocks
    private OperacaoServiceImpl operacaoService;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private OperacaoRepository operacaoRepository;

    private Conta conta1, conta2;
    private Cliente cliente;
    private List<Operacao> list = new ArrayList<>();
    private Operacao operacao;

    @Before
    public void setUP() {


        conta1 = new Conta(1, "01/01/2018", 0.0);

        conta2 = new Conta(2, "01/01/2018", 0.0);

        cliente = new Cliente(1, "Carlos", "11122233344", conta1, "01/01/2018");

        operacao = new Operacao(2, 0.0, "23/02/2018" , null , null, null );

    }



    //findAccount
    @Test
    public void findAccountTestOK() {
        when(contaRepository.findById(anyInt())).thenReturn(Optional.of(conta1));
        Conta account = operacaoService.findAccount(1);
        assertNotNull(account);
    }

    @Test
    public void findAccountTestException(){
        when(contaRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        exception.expect(ObjectNotFoundException.class);
        Conta account = operacaoService.findAccount(1);
    }


    //TipoOperacao
    @Test
    public void typeOperationDepositoTestOK(){
        when(contaRepository.findById(anyInt())).thenReturn(Optional.ofNullable(conta1));
        operacao.setTipoOperacao(TipoOperacao.DEPOSITO);
        operacao.setIdContaDestino(conta1);
        Operacao op = operacaoService.typeOperation(this.operacao);
        verify((operacaoRepository), times(1)).saveAndFlush(any());
        verify(contaRepository, times(1)).saveAndFlush(any());
        assertNotNull(op);
    }

    @Test
    public void typeOperationDepositoTestExeption(){
        when(contaRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        exception.expect(ObjectNotFoundException.class);
        operacao.setTipoOperacao(TipoOperacao.DEPOSITO);
        operacao.setIdContaDestino(conta1);
        operacaoService.typeOperation(operacao);
    }


    @Test
    public void typeOperationSaqueTestOK(){
        when(contaRepository.findById(anyInt())).thenReturn(Optional.ofNullable(conta1));
        operacao.setTipoOperacao(TipoOperacao.SAQUE);
        operacao.setIdContaOrigem(conta1);
        Operacao op = operacaoService.typeOperation(this.operacao);
        verify((operacaoRepository), times(1)).saveAndFlush(any());
        verify(contaRepository, times(1)).saveAndFlush(any());
        assertNotNull(op);
    }

    @Test
    public void typeOperationSaqueTestExeption(){
        when(contaRepository.findById(anyInt())).thenReturn(Optional.ofNullable(conta1));
        exception.expect(UnprocessableEntity.class);
        operacao.setTipoOperacao(TipoOperacao.SAQUE);
        operacao.setIdContaOrigem(conta1);
        operacao.setValor(1.0);
        operacaoService.typeOperation(operacao);
    }


    @Test
    public void typeOperationTransferenciaTestOK(){
        when(contaRepository.findById(1)).thenReturn(Optional.ofNullable(conta1));
        when(contaRepository.findById(2)).thenReturn(Optional.ofNullable(conta2));
        operacao.setTipoOperacao(TipoOperacao.TRANSFERENCIA);
        operacao.setIdContaOrigem(conta1);
        operacao.setIdContaDestino(conta2);
        Operacao op = operacaoService.typeOperation(this.operacao);
        verify((operacaoRepository), times(2)).saveAndFlush(any());
        verify(contaRepository, times(2)).saveAndFlush(any());
        assertNotNull(op);
    }


    @Test
    public void typeOperationTransferenciaTestException(){
        when(contaRepository.findById(1)).thenReturn(Optional.ofNullable(conta1));
        when(contaRepository.findById(2)).thenReturn(Optional.ofNullable(conta2));
        exception.expect(EqualAccountTransfer.class);
        operacao.setTipoOperacao(TipoOperacao.TRANSFERENCIA);
        operacao.setIdContaOrigem(conta1);
        operacao.setIdContaDestino(conta1);
        operacaoService.typeOperation(this.operacao);
    }

    @Test
    public void findExtractTestOK() {
        list.add(operacao);
        when(operacaoRepository.operacao(anyInt())).thenReturn(list);
        operacao.setTipoOperacao(TipoOperacao.TRANSFERENCIA);
        operacao.setIdContaOrigem(conta1);
        operacao.setValor(2.0);
        List<Operacao> returnMethod = operacaoService.findExtract(1);
        assertFalse(returnMethod.isEmpty());
        assertEquals(Double.valueOf(-2.0), returnMethod.get(0).getValor());
    }

    @Test
    public void findExtractTestEmpty() {
        when(operacaoRepository.operacao(anyInt())).thenReturn(list);
        List<Operacao> returnMethod = operacaoService.findExtract(1);
        assertTrue(returnMethod.isEmpty());
    }




}

