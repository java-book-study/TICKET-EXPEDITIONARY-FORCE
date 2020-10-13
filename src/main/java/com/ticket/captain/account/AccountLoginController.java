package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
@Slf4j
public class AccountLoginController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @PostMapping("/testlogin")
    public ResponseEntity simple_login(@RequestBody AccountCreateDto accountCreateDto, Errors errors) {
        Account findAccount = accountRepository.findAccount(accountCreateDto.getEmail(), accountCreateDto.getPassword());
        if (findAccount == null) {
            return ResponseEntity.badRequest().body(null);
        }else return ResponseEntity.ok(findAccount);
    }
}
