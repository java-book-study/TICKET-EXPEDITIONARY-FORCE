package com.ticket.captain.account;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.List;

@Getter
public class UserAccount extends User {

    public Account account;

    public UserAccount(Account account) {
        super(account.getEmail(), account.getPassword(), Collections.singleton(new SimpleGrantedAuthority(account.getRole().toString())));
        this.account = account;
    }
}
