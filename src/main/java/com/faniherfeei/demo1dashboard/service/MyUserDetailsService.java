package com.faniherfeei.demo1dashboard.service;

import com.faniherfeei.demo1dashboard.model.Account;
import com.faniherfeei.demo1dashboard.model.UserPrincipal;
import com.faniherfeei.demo1dashboard.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsernameWithPermissions(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserPrincipal(account);
    }
}