
package com.agenciaBancaria.service;

import static org.junit.Assert.assertEquals;
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
import com.agenciaBancaria.domain.Cliente;
import com.agenciaBancaria.domain.Conta;
import com.agenciaBancaria.dto.ClienteDTO;
import com.agenciaBancaria.repository.ClienteRepository;
import com.agenciaBancaria.repository.ContaRepository;
import com.agenciaBancaria.service.exception.ObjectNotFoundException;
import com.agenciaBancaria.service.impl.ClienteServiceImpl;


public class ClienteServiceImplTest extends AbstractTest {


    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ContaRepository contaRepository;

    private Conta conta;
    private Cliente cliente;
    private ClienteDTO clienteDTO;
    private List<Cliente> list;

    @Before
    public void setUP() {


        conta = new Conta(1, "01/01/2018", 0.0);

        cliente = new Cliente(1, "Carlos", "11122233344", conta, "01/01/2018");
        clienteDTO = new ClienteDTO(1, "10545236501" , "Carlos","02/05/2018");

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
        when(clienteRepository.save(any(Cliente.class))).thenReturn(null);

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
    public void findAllTestOK() {
        when(clienteRepository.findAll()).thenReturn(list);

        logger.info("Inicio do teste");

        List<Cliente> returnMethod = clienteService.findAllCustomer();
        assertNotNull(returnMethod);
        assertEquals(returnMethod.get(0).getNome(), "Carlos");
        logger.info("Fim do teste");
    }

    @Test
    public void findAllisEmpty() {
        list.clear();
        when(clienteRepository.findAll()).thenReturn(list);

        List<Cliente> returnMethod = clienteService.findAllCustomer();
        assertTrue(returnMethod.isEmpty());

    }



    //findIdCustomer
    @Test
    public void findCustomerTestOK() {
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));
        Cliente c = clienteService.findIdCustomer(1);
        assertNotNull(c);
    }

    @Test
    public void findAccountTestException(){
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        exception.expect(ObjectNotFoundException.class);
        Cliente c = clienteService.findIdCustomer(1);
    }

    //DeleteCustomer
    @Test
    public void deleteCustomerTestOK() {
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));
        clienteService.deleteCustomer(1);
        verify((clienteRepository), times(1)).deleteById(any());
        verify((contaRepository), times(1)).deleteById(any());
    }

    @Test
    public void deleteCustomerTestException() {
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));
        exception.expect(ObjectNotFoundException.class);
        clienteService.deleteCustomer(null);
    }


    //UpdateCustomer
    @Test
    public void updateCustomerTestOK() {
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.ofNullable(cliente));
        when(clienteRepository.saveAndFlush(any())).thenReturn(cliente);
        Cliente c = clienteService.updateCustomer(cliente);
        assertNotNull(c);
    }


    @Test
    public void updateCustomerTestException() {
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        exception.expect(ObjectNotFoundException.class);
        clienteService.updateCustomer(cliente);
    }

    //FROMDTO
    @Test
    public void fromDTOTestOK() {
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.ofNullable(cliente));
        Cliente c = clienteService.fromDTO(clienteDTO);
        assertNotNull(c);
    }


    @Test
    public void fromDTOTestException() {
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        exception.expect(ObjectNotFoundException.class);
        clienteService.updateCustomer(cliente);
    }





}

