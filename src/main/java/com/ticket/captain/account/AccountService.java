package com.ticket.captain.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final TemplateEngine templateEngine;

    public Account createAccount(SignUpForm signUpForm){
        Account account = new Account();
        account.setLoginId(signUpForm.getLoginId());
        account.setEmail(signUpForm.getEmail());
        account.setName(signUpForm.getName());
        account.setNickname(signUpForm.getNickname());
        account.setPassword(passwordEncoder.encode(signUpForm.getPassword()));

        account.generateEmailCheckToken();

        return accountRepository.save(account);

    }

}
