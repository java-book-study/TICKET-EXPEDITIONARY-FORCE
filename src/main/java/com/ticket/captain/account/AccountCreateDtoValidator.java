package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class AccountCreateDtoValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(AccountCreateDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountCreateDto accountCreateDto = (AccountCreateDto) target;
    }
}