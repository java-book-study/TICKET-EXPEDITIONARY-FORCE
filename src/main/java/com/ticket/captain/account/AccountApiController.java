package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountResponseDto;
import com.ticket.captain.account.dto.AccountUpdateRequestDto;
import com.ticket.captain.response.ApiResponseDto;
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
@RequestMapping("/api/account/**")
public class AccountApiController {

    private final AccountService accountService;

    @GetMapping("")
    public ApiResponseDto<?> accountList(@PageableDefault Pageable pageable){

        Page<AccountResponseDto> result = accountService.findAccountList(pageable);

        return ApiResponseDto.createOK(result);
    }

    @GetMapping("{id}")
    public ApiResponseDto<?> accountDetail(@PathVariable Long id){

        AccountResponseDto result = accountService.findAccountDetail(id);

        return ApiResponseDto.createOK(result);
    }

    @PostMapping("{id}")
    public ApiResponseDto<?> accountUpdate(@PathVariable Long id, @RequestBody @Valid AccountUpdateRequestDto updateRequestDto){

        accountService.accountUpdate(id, updateRequestDto);

        return ApiResponseDto.DEFAULT_OK;
    }

}
