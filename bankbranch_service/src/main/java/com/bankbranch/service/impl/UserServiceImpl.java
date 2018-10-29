package com.bankbranch.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bankbranch.authorizations.UserSS;
import com.bankbranch.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    public UserSS authenticated(){
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e){
            throw new UsernameNotFoundException("Authenticated fail!");
        }

    }
}

