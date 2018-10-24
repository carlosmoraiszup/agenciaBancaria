package com.bankbranch.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bankbranch.domain.Customer;
import com.bankbranch.repository.CustomerRepository;
import com.bankbranch.security.UserSS;
import com.bankbranch.service.UserDetailsService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByCpf(cpf);
        if(customer == null)
            throw new UsernameNotFoundException(cpf);

        return new UserSS(customer.getId(), customer.getCpf(), customer.getPassword(), customer.getPerfis());
    }
}
