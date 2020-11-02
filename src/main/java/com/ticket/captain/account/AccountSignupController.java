package com.ticket.captain.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.captain.account.dto.AccountCreateDto;
import com.ticket.captain.common.ErrorsResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountSignupController {

    private final ObjectMapper objectMapper;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @PostMapping
    public ResponseEntity createAccount(@RequestBody @Valid AccountCreateDto accountCreateDto, Errors errors) throws URISyntaxException {
        if(errors.hasErrors()){
            return badRequest(errors);
        }
        Account account = accountService.createAccount(accountCreateDto);
        return ResponseEntity.created(new URI("/sign-up/complete")).body(account);
    }


    @GetMapping("/check-email-token")
    public ApiResponseDto<?> checkEmailToken(String token, String email) {
        Account account = accountRepository.findByEmail(email);

        if(account == null){
            return ApiResponseDto.NOT_FOUND(new AccountDto.Response(account));
        }
        if(!account.isValidToken(token)){
            return ApiResponseDto.BAD_REQUEST(new AccountDto.Response(account));
        }

        /**
         * completeSignUp 내부의 login 메서드 제외함. 해당 view 에서는 이메일 인증 완료의 여부에 대한 내용이 있어야 하며,
         * login을 할 수 있는 페이지로 갈 수 있어야함
         */
        accountService.completeSignUp(account);
        return ApiResponseDto.createOK(new AccountDto.Response(account));
    }

    private ResponseEntity badRequest(Errors errors){

        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(new AccountCreateDtoValidator(accountRepository));
    }
}
