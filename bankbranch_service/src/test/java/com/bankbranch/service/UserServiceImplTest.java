package com.bankbranch.service;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bankbranch.AbstractTest;
import com.bankbranch.service.impl.UserServiceImpl;


public class UserServiceImplTest extends AbstractTest {


    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUP(){
    }



    @Test
    public void authenticated_returnsThrowException() {
        exception.expect(UsernameNotFoundException.class);
        when(userService.authenticated()).thenReturn(null);
    }


}

