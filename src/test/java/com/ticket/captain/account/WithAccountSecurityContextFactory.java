package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountCreateDto;
import com.ticket.captain.account.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;

@RequiredArgsConstructor
public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

    private final AccountService accountService;

    @Override
    public SecurityContext createSecurityContext(WithAccount withAccount) {

        if(!accountService.existByEmail( withAccount.email() )){
            AccountDto accountDto = accountService.createAccount(
                    AccountCreateDto.builder()
                            .email(withAccount.email())
                            .password("1q2w3e4r")
                            .nickname(withAccount.nickname())
                            .build());
            accountService.roleAppoint(accountDto.getId(), withAccount.role());
        }

        UserDetails principal = accountService.loadUserByUsername(withAccount.email());
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "",
                Collections.singleton(new SimpleGrantedAuthority(withAccount.role().name())));
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }
}
