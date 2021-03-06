package com.ticket.captain.account;

import com.ticket.captain.account.authentication.AuthenticationResource;
import com.ticket.captain.account.authentication.AuthenticationResponse;
import com.ticket.captain.account.dto.AccountCreateDto;
import com.ticket.captain.account.dto.AccountDto;
import com.ticket.captain.common.ErrorsResource;
import com.ticket.captain.response.ApiResponseDto;
import com.ticket.captain.security.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/sign-up", produces = MediaTypes.HAL_JSON_VALUE)
public class AccountSignupController {

    private final AccountService accountService;
    private final AccountCreateDtoValidator accountCreateDtoValidator;
    private final Jwt jwt;

    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody AccountCreateDto accountCreateDto) {
        AccountDto accountDto = accountService.createAccount(accountCreateDto);
        String jwtToken = jwt.createToken(
                accountDto.getId(), accountDto.getName(), accountDto.getEmail(), Role.UNAUTH.name());
        AuthenticationResponse response = new AuthenticationResponse(jwtToken, accountDto);
        EntityModel<AuthenticationResponse> responseEntityModel = AuthenticationResource.signUpOf(response);
        responseEntityModel.add(Link.of("/docs/index.html#signUp-account").withRel("profile"));
        return ResponseEntity.ok(responseEntityModel);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(accountCreateDtoValidator);
    }

}
