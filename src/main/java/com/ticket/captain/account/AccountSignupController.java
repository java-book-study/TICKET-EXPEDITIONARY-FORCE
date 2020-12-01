package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountCreateDto;
import com.ticket.captain.account.dto.AccountDto;
import com.ticket.captain.common.ErrorsResource;
import com.ticket.captain.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountSignupController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final AccountCreateDtoValidator accountCreateDtoValidator;

    @PostMapping
    public ApiResponseDto<?> createAccount(@RequestBody @Valid AccountCreateDto accountCreateDto, Errors errors) {

        if (errors.hasErrors()) {
            return badRequest(errors);
        }

        Account account = accountService.createAccount(accountCreateDto);
        return ApiResponseDto.createOK(new AccountDto(account));
    }

    @GetMapping("/check-email-token")
    public ApiResponseDto<?> checkEmailToken(String token, String email) {

        Account account = accountRepository.findByEmail(email);

        if (account == null){
            return ApiResponseDto.NOT_FOUND(new AccountDto());
        }

        if (!account.isValidToken(token)) {
            return ApiResponseDto.BAD_REQUEST(new AccountDto(account));
        }

        /**
         * completeSignUp 내부의 login 메서드 제외함. 해당 view 에서는 이메일 인증 완료의 여부에 대한 내용이 있어야 하며,
         * login을 할 수 있는 페이지로 갈 수 있어야함
         */
        return ApiResponseDto.createOK(new AccountDto(account));
    }

    private ApiResponseDto<?> badRequest(Errors errors) {
        return ApiResponseDto.BAD_REQUEST(new ErrorsResource(errors));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(accountCreateDtoValidator);
    }

}
