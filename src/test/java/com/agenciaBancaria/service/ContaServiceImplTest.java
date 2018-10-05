
package com.agenciaBancaria.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.agenciaBancaria.AbstractTest;
import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.repository.ClienteRepository;
import com.agenciaBancaria.repository.ContaRepository;
import com.agenciaBancaria.service.impl.ClienteServiceImpl;
import com.agenciaBancaria.service.impl.ContaServiceImpl;


public class ContaServiceImplTest extends AbstractTest {


    @InjectMocks
    private ContaServiceImpl contaService;

    @Mock
    private ClienteService clienteService;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    private Conta conta;
    private Cliente cliente;

    @Before
    public void setUP() {

      conta = new Conta(1, "26/08/2018", 180.12);
      cliente = new Cliente(1, "Carlos", "11122233344", conta, "01/01/2018");


    }



    //registerAccount
    @Test
    public void registerAccountEXCEPTION() {

        when(clienteRepository.findByCpf(anyString())).thenReturn(cliente);
        exception.expect(NullPointerException.class);

        contaService.registerAccount(cliente);


    }

    @Test
    public void registerAccountOK() {

        when(clienteService.registerCustomer(any(Cliente.class), any(Conta.class))).thenReturn(cliente);
        when(clienteRepository.findByCpf(anyString())).thenReturn(null);
        ArgumentCaptor<Conta> arg = ArgumentCaptor.forClass(Conta.class);

        Cliente clienteResponse = contaService.registerAccount(cliente);

        verify(contaRepository, times(1)).saveAndFlush(arg.capture());


    }

}

