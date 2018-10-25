package com.bankbranch.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bankbranch.domain.enums.Profile;

public class UserSS implements UserDetails {

    private Integer id;
    private String cpf;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSS(){ }

    public UserSS(Integer id, String cpf, String password,
            Set<Profile> perfis) {
        this.id = id;
        this.cpf = cpf;
        this.password = password;
        this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescription())).collect(Collectors.toList());
    }

    public Integer getId(){
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(Profile profile){
        return getAuthorities().contains(new SimpleGrantedAuthority(profile.getDescription()));
    }
}
