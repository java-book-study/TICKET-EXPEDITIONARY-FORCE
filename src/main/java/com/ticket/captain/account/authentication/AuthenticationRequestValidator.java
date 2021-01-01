package com.ticket.captain.account.authentication;

import com.ticket.captain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class AuthenticationRequestValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(AuthenticationRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AuthenticationRequest authenticationRequest = (AuthenticationRequest) target;

        if (authenticationRequest.getPrincipal().length() < 4
                && authenticationRequest.getPrincipal().length() > 20) {
            errors.rejectValue("principal", "invalid.Principal", new Object[]{authenticationRequest.getPrincipal()}, "아이디는 4자 이상 13자 이하입니다.");
        }

        if (authenticationRequest.getCredentials().length() < 4
                && authenticationRequest.getCredentials().length() > 13) {
            errors.rejectValue("credentials", "invalid.Credentials", new Object[]{authenticationRequest.getCredentials()}, "비밀번호는 4자 이상 13자 이하입니다.");
        }
    }
}
