package com.bankbranch.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bankbranch.AbstractTest;
import com.bankbranch.domain.Account;
import com.bankbranch.domain.Customer;
import com.bankbranch.repository.CustomerRepository;
import com.bankbranch.security.UserSS;
import com.bankbranch.service.impl.UserDetailsServiceImpl;
import com.bankbranch.service.impl.UserServiceImpl;


public class UserServiceImplTest extends AbstractTest {


    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void authenticated_returnsSuccess() {
        UserSS userSS = userService.authenticated();
        assertNotNull(userSS);
    }

    @Test
    public void authenticated_returnsThrowException() {
        exception.expect(UsernameNotFoundException.class);
        when(userService.authenticated()).thenReturn(null);
    }


}

