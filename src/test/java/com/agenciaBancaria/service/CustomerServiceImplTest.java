
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
import com.bankbranch.domain.Account;
import com.bankbranch.domain.Customer;
import com.bankbranch.dto.CustomerDTO;
import com.bankbranch.repository.ClienteRepository;
import com.bankbranch.repository.AccountRepository;
import com.bankbranch.service.exception.ObjectNotFoundException;
import com.bankbranch.service.impl.CustomerServiceImpl;


public class CustomerServiceImplTest extends AbstractTest {


    @InjectMocks
    private CustomerServiceImpl clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private AccountRepository accountRepository;

    private Account account;
    private Customer customer;
    private CustomerDTO customerDTO;
    private List<Customer> list;

    @Before
    public void setUP() {


        account = new Account(1, "01/01/2018", 0.0);

        customer = new Customer(1, "Carlos", "11122233344", account, "01/01/2018");
        customerDTO = new CustomerDTO(1, "10545236501" , "Carlos","02/05/2018");

        list = new ArrayList<>();
        list.add(customer);
    }


     //registerCustomer
    @Test
    public void registerCustomerOK() {
        when(clienteRepository.save(any(Customer.class))).thenReturn(customer);

        Customer returnMethod = clienteService.registerCustomer(customer, account);
        assertNotNull(returnMethod);
        assertEquals(returnMethod.getNameCustomer(), "Carlos");

    }

    @Test
    public void registerCustomerWithReturnNull() {
        when(clienteRepository.save(any(Customer.class))).thenReturn(null);

        Customer returnMethod = clienteService.registerCustomer(customer, account);
        assertNull(returnMethod);
    }

    @Test
    public void registerCustomerWithContaNull() {
        Customer returnMethod = clienteService.registerCustomer(customer, null);
        assertNull(returnMethod);
    }


    @Test
    public void registerCustomerException() {
        exception.expect(NullPointerException.class);

        clienteService.registerCustomer(null, account);
    }

   //findAllCustomer
    @Test
    public void findAllTestOK() {
        when(clienteRepository.findAll()).thenReturn(list);

        logger.info("Inicio do teste");

        List<Customer> returnMethod = clienteService.findAllCustomer();
        assertNotNull(returnMethod);
        assertEquals(returnMethod.get(0).getNameCustomer(), "Carlos");
        logger.info("Fim do teste");
    }

    @Test
    public void findAllisEmpty() {
        list.clear();
        when(clienteRepository.findAll()).thenReturn(list);

        List<Customer> returnMethod = clienteService.findAllCustomer();
        assertTrue(returnMethod.isEmpty());

    }



    //findIdCustomer
    @Test
    public void findCustomerTestOK() {
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(customer));
        Customer c = clienteService.findIdCustomer(1);
        assertNotNull(c);
    }

    @Test
    public void findAccountTestException(){
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        exception.expect(ObjectNotFoundException.class);
        Customer c = clienteService.findIdCustomer(1);
    }

    //DeleteCustomer
    @Test
    public void deleteCustomerTestOK() {
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(customer));
        clienteService.deleteCustomer(1);
        verify((clienteRepository), times(1)).deleteById(any());
        verify((accountRepository), times(1)).deleteById(any());
    }

    @Test
    public void deleteCustomerTestException() {
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(customer));
        exception.expect(ObjectNotFoundException.class);
        clienteService.deleteCustomer(null);
    }


    //UpdateCustomer
    @Test
    public void updateCustomerTestOK() {
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.ofNullable(customer));
        when(clienteRepository.saveAndFlush(any())).thenReturn(customer);
        Customer c = clienteService.updateCustomer(customer);
        assertNotNull(c);
    }


    @Test
    public void updateCustomerTestException() {
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        exception.expect(ObjectNotFoundException.class);
        clienteService.updateCustomer(customer);
    }

    //FROMDTO
    @Test
    public void fromDTOTestOK() {
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.ofNullable(customer));
        Customer c = clienteService.fromDTO(customerDTO);
        assertNotNull(c);
    }


    @Test
    public void fromDTOTestException() {
        when(clienteRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        exception.expect(ObjectNotFoundException.class);
        clienteService.updateCustomer(customer);
    }





}

