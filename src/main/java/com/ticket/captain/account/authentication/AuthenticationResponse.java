package com.ticket.captain.account.authentication;

import com.ticket.captain.account.dto.AccountDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthenticationResponse {

    private final String token;

    private final AccountDto accountDto;


}
