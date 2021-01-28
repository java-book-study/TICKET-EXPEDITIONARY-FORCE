package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountCreateDto;
import com.ticket.captain.account.dto.AccountDto;
import com.ticket.captain.common.Address;
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

        String nickname = withAccount.value();

        Address address = new Address("seoul", "gangnam", "111");

        AccountCreateDto accountCreateDto = AccountCreateDto.builder()
                .email(nickname + "@naver.com")
                .password("1q2w3e4r")
                .nickname(nickname)
                .name("eunseong")
                .address(address)
                .build();
        AccountDto accountDto = accountService.createAccount(accountCreateDto);

        UserDetails principal = accountService.loadUserByUsername(accountDto.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "", Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_USER.name())));
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }
}
