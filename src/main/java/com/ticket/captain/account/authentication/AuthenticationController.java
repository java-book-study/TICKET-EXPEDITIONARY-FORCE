package com.ticket.captain.account.authentication;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.AccountService;
import com.ticket.captain.account.Role;
import com.ticket.captain.account.dto.AccountDto;
import com.ticket.captain.exception.EmailNotExistedException;
import com.ticket.captain.exception.UnauthorizedException;
import com.ticket.captain.response.ApiResponseDto;
import com.ticket.captain.security.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api", produces = MediaTypes.HAL_JSON_VALUE)
public class AuthenticationController {

    private final AccountService accountService;

    private final Jwt jwt;

    private final AuthenticationRequestValidator validator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {webDataBinder.addValidators(validator);}

    @PostMapping("auth")
    public ResponseEntity<?> authenticate (@RequestBody @Valid AuthenticationRequest request) {
        try {
            String email = request.getPrincipal();
            String password = request.getCredentials();

            Account account = accountService.authenticate(email, password);
            String role = (account.getRole() == null) ? Role.UNAUTH.name() : account.getRole().name();

            String jwtToken = jwt.createToken(
                    account.getId(),
                    account.getName(), account.getEmail(), role);
            jwt.getAuthentication(jwtToken);
            AccountDto accountDto = AccountDto.of(account);

            AuthenticationResponse response = new AuthenticationResponse(jwtToken, accountDto);
            EntityModel<AuthenticationResponse> responseEntityModel = AuthenticationResource.loginOf(response);
            responseEntityModel.add(Link.of("/docs/index.html#login-account").withRel("profile"));

            return ResponseEntity.ok(responseEntityModel);
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("check-email-token")
    public ResponseEntity<?> checkEmailToken(@RequestParam String token, @RequestParam String email) {
        Account account = accountService.findByEmail(email);
        if (account == null) {
            throw new EmailNotExistedException(email);
        }
        accountService.completeSignUp(account, token);

        String jwtToken = jwt.createToken(
                account.getId(),
                account.getName(), account.getEmail(), account.getRole().name());
        jwt.getAuthentication(jwtToken);
        AccountDto accountDto = AccountDto.of(account);

        AuthenticationResponse response = new AuthenticationResponse(jwtToken, accountDto);
        EntityModel<AuthenticationResponse> responseEntityModel = AuthenticationResource.loginOf(response);
        responseEntityModel.add(Link.of("/docs/index.html#email-auth-account").withRel("profile"));

        return ResponseEntity.ok(responseEntityModel);
    }
}
