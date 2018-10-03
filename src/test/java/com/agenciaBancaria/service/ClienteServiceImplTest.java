/*

package com.agenciaBancaria.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.agenciaBancaria.AbstractTest;
import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.repository.ClienteRepository;



public class ClienteServiceImplTest extends AbstractTest {


    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    private Conta conta;
    private Cliente cliente;
    private List<Cliente> list;

    @Before
    public void setUP() {


        conta = new Conta(1, "01/01/2018", 0.0);

        cliente = new Cliente(1, "Carlos", "11122233344", conta, "01/01/2018");

        list = new ArrayList<>();
        list.add(cliente);
    }


     //registerCustomer



    @Test
    public void registerCustomerOK() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente returnMethod = clienteService.registerCustomer(cliente, conta);
        assertNotNull(returnMethod);
        assertEquals(returnMethod.getNome(), "Carlos");

    }

    @Test
    public void registerCustomerWithReturnNull() {
        Cliente returnMethod = clienteService.registerCustomer(cliente, conta);
        assertNull(returnMethod);
    }

    @Test
    public void registerCustomerWithContaNull() {
        Cliente returnMethod = clienteService.registerCustomer(cliente, null);
        assertNull(returnMethod);
    }


    @Test
    public void registerCustomerException() {
        exception.expect(NullPointerException.class);

        clienteService.registerCustomer(null, conta);
    }

   //findAll



    @Test
    public void findAllTest() {
        when(clienteRepository.findAll()).thenReturn(list);

        logger.info("Inicio do teste");

        List<Cliente> returnMethod = clienteService.findAllCustomer();
        assertNotNull(returnMethod);
        assertEquals(returnMethod.get(0).getNome(), "Carlos");
        logger.info("Fim do teste");
    }
}
*/
