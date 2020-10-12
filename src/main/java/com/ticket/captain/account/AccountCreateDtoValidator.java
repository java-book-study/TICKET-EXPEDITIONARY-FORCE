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
        if(accountRepository.existsByNickname(accountCreateDto.getNickname())){
            errors.rejectValue("nickname", "invalid.Nickname", new Object[]{accountCreateDto.getNickname()}, "이미 사용중인 닉네임입니다.");
        }
        if(accountRepository.existsByEmail(accountCreateDto.getEmail())){
            errors.rejectValue("email", "invalid,Email", new Object[]{accountCreateDto.getEmail()}, "이미 사용중인 이메일입니다.");
        }
        if(accountRepository.existsByLoginId(accountCreateDto.getLoginId())){
            errors.rejectValue("loginId", "invalid.LoginId", new Object[]{accountCreateDto.getLoginId()}, "이미 사용중인 아이디입니다.");
        }
    }
}
