package com.ticket.captain.account.authentication;

import lombok.Getter;

@Getter
public class AuthenticationRequest {

    private String principal;
    private String credentials;

    public AuthenticationRequest(String principal, String credentials) {
        this.principal = principal;
        this.credentials = credentials;
    }
}
