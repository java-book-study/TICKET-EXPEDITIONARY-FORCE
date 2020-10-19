package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountResponseDto;
import com.ticket.captain.account.dto.AccountUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 은성님 controller 코드
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountApiController {

    private final AccountService accountService;

    @GetMapping("/api/account")
    public ResponseEntity accountList(@PageableDefault Pageable pageable){

        Page<AccountResponseDto> result = accountService.findAccountList(pageable);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/api/account/{id}")
    public ResponseEntity accountDetail(@PathVariable Long id){

        AccountResponseDto result = accountService.findAccountDetail(id);

        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/api/account/{id}")
    public ResponseEntity accountUpdate(@PathVariable Long id, @RequestBody @Valid AccountUpdateRequestDto updateRequestDto){

        accountService.accountUpdate(id, updateRequestDto);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
