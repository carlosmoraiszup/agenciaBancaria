//package com.bankbranch.service;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyString;
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
//import com.bankbranch.dto.CustomerDTO;
//import com.bankbranch.repository.AccountRepository;
//import com.bankbranch.repository.CustomerRepository;
//import com.bankbranch.service.exception.EmptyException;
//import com.bankbranch.service.exception.ExistingAccountException;
//import com.bankbranch.service.exception.LengthCpfException;
//import com.bankbranch.service.exception.ObjectNotFoundException;
//import com.bankbranch.service.impl.CustomerServiceImpl;
//
//
//public class CustomerServiceImplTest extends AbstractTest {
//
//
//    @InjectMocks
//    private CustomerServiceImpl customerService;
//
//    @Mock
//    private CustomerRepository customerRepository;
//    @Mock
//    private AccountRepository accountRepository;
//    @Mock
//    private AccountService accountService;
//
//    private Account account;
//    private Customer customer;
//    private CustomerDTO customerDTO;
//    private List<Customer> list;
//
//    @Before
//    public void setUP() {
//
//
//        account = new Account(1, "01/01/2018", 0.0);
//
//        customer = new Customer(1, "Carlos", "10575823607", account, "01/01/2018");
//        customerDTO = new CustomerDTO(1, "10545236501", "Carlos", "02/05/2018");
//
//        list = new ArrayList<>();
//        list.add(customer);
//    }
//
//
//
//
//
//    //registerCustomer
//    @Test
//    public void registerCustomerOK() {
//        when(customerRepository.findByCpf(anyString())).thenReturn(null);
//        when(accountService.registerAccount()).thenReturn(account);
//        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);
//
//        customer = customerService.registerCustomer(customer);
//        assertNotNull(customer);
//        assertEquals(customer.getNameCustomer(), "Carlos");
//    }
//
//    @Test
//    public void registerCustomerisNull(){
//        exception.expect(ExistingAccountException.class);
//        customerService.registerCustomer(null);
//    }
//
//    @Test
//    public void registerCustomerException(){
//        when(customerRepository.findByCpf(anyString())).thenReturn(customer);
//        when(accountService.registerAccount()).thenReturn(account);
//        exception.expect(ExistingAccountException.class);
//        customerService.registerCustomer(customer);
//    }
//
//
//    @Test
//    public void registerCustomerWithReturnNull() {
//        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
//        when(accountService.registerAccount()).thenReturn(account);
//
//        Customer returnMethod = customerService.registerCustomer(customer);
//        assertNull(returnMethod);
//    }
//
//    @Test
//    public void registerCustomerWithCPFisNull() {
//        Customer customerTest = new Customer(1, "Carlos", null, account, "01/01/2018");
//        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
//        exception.expect(LengthCpfException.class);
//        customerService.registerCustomer(customerTest);
//    }
//
//    @Test
//    public void registerCustomerWithCPFisEmpty() {
//        Customer customerTest = new Customer(1, "Carlos", "", account, "01/01/2018");
//        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(null);
//        exception.expect(LengthCpfException.class);
//        customerService.registerCustomer(customerTest);
//    }
//
//
//    @Test
//    public void registerCustomerWithAccountNull() {
//        when(customerRepository.findByCpf(anyString())).thenReturn(null);
//        when(accountService.registerAccount()).thenReturn(null);
//        exception.expect(EmptyException.class);
//
//        customerService.registerCustomer(customer);
//
//    }
//
//
//
//   //findAllCustomers
//    @Test
//    public void findAllTestOK() {
//        when(customerRepository.findAll()).thenReturn(list);
//
//        List<Customer> returnMethod = customerService.findAllCustomers();
//        assertNotNull(returnMethod);
//        assertEquals(returnMethod.get(0).getNameCustomer(), "Carlos");
//    }
//
//    @Test
//    public void findAllisEmpty() {
//        list.clear();
//        when(customerRepository.findAll()).thenReturn(list);
//
//        List<Customer> returnMethod = customerService.findAllCustomers();
//        assertTrue(returnMethod.isEmpty());
//
//    }
//
//    //findIdCustomer
//    @Test
//    public void findCustomerTestOK() {
//        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));
//        Customer c = customerService.findIdCustomer(1);
//        assertNotNull(c);
//    }
//
//    @Test
//    public void findAccountTestException(){
//        when(customerRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
//        exception.expect(ObjectNotFoundException.class);
//        Customer c = customerService.findIdCustomer(1);
//    }
//
//    //DeleteCustomer
//    @Test
//    public void deleteCustomerTestOK() {
//        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));
//        customerService.deleteCustomer(1);
//        verify((customerRepository), times(1)).deleteById(any());
//        verify((accountRepository), times(1)).deleteById(any());
//    }
//
//    @Test
//    public void deleteCustomerTestException() {
//        when(customerRepository.findById(anyInt())).thenReturn(Optional.of(customer));
//        exception.expect(ObjectNotFoundException.class);
//        customerService.deleteCustomer(null);
//    }
//
//
//    //UpdateCustomer
//    @Test
//    public void updateCustomerTestOK() {
//        when(customerRepository.findById(anyInt())).thenReturn(Optional.ofNullable(customer));
//        when(customerRepository.saveAndFlush(any())).thenReturn(customer);
//        Customer c = customerService.updateCustomer(customer);
//        assertNotNull(c);
//    }
//
//
//    @Test
//    public void updateCustomerTestException() {
//        when(customerRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
//        exception.expect(ObjectNotFoundException.class);
//        customerService.updateCustomer(customer);
//    }
//
//    //FROMDTO
//    @Test
//    public void fromDTOTestOK() {
//        when(customerRepository.findById(anyInt())).thenReturn(Optional.ofNullable(customer));
//        Customer c = customerService.fromDTO(customerDTO);
//        assertNotNull(c);
//    }
//
//
//    @Test
//    public void fromDTOTestException() {
//        when(customerRepository.findById(anyInt())).thenReturn(Optional.ofNullable(null));
//        exception.expect(ObjectNotFoundException.class);
//        customerService.updateCustomer(customer);
//    }
//
//
//
//
//
//}
//
