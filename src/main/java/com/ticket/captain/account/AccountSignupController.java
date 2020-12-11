package com.ticket.captain.account;

import com.ticket.captain.account.authentication.AuthenticationResponse;
import com.ticket.captain.account.dto.AccountCreateDto;
import com.ticket.captain.account.dto.AccountDto;
import com.ticket.captain.common.ErrorsResource;
import com.ticket.captain.response.ApiResponseDto;
import com.ticket.captain.security.Jwt;
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
    private final AccountCreateDtoValidator accountCreateDtoValidator;
    private final Jwt jwt;

    @PostMapping
    public ApiResponseDto<AuthenticationResponse> createAccount(@Valid @RequestBody AccountCreateDto accountCreateDto) {
        AccountDto accountDto = accountService.createAccount(accountCreateDto);
        String jwtToken = jwt.createToken(
                accountDto.getId(), accountDto.getName(), accountDto.getEmail(), Role.UNAUTH.toString());
        return ApiResponseDto.createOK(new AuthenticationResponse(jwtToken, accountDto));
    }



    private ApiResponseDto<?> badRequest(Errors errors) {
        return ApiResponseDto.BAD_REQUEST(new ErrorsResource(errors));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(accountCreateDtoValidator);
    }

}
