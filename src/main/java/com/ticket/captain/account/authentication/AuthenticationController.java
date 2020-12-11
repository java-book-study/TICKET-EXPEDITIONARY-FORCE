package com.ticket.captain.account.authentication;

import com.ticket.captain.account.Account;
import com.ticket.captain.account.AccountService;
import com.ticket.captain.account.Role;
import com.ticket.captain.account.dto.AccountDto;
import com.ticket.captain.exception.NotFoundException;
import com.ticket.captain.exception.UnauthorizedException;
import com.ticket.captain.response.ApiResponseDto;
import com.ticket.captain.security.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AccountService accountService;

    private final Jwt jwt;

    @PostMapping("api/auth")
    public ApiResponseDto<AuthenticationResponse> authenticate (@RequestBody AuthenticationRequest request) {
        try {
            String email = request.getPrincipal();
            String password = request.getCredentials();

            Account account = accountService.authenticate(email, password);
            String role = (account.getRole() == null) ? Role.UNAUTH.toString() : account.getRole().toString();

            String jwtToken = jwt.createToken(
                    account.getId(),
                    account.getName(), account.getEmail(), role);
            jwt.getAuthentication(jwtToken);
            AccountDto accountDto = AccountDto.of(account);

            return ApiResponseDto.createOK(
                    new AuthenticationResponse(jwtToken, accountDto)
            );
        } catch (UnauthorizedException e) {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("api/check-email-token")
    public ApiResponseDto<AuthenticationResponse> checkEmailToken(String token,  String email) {
        Account account = accountService.findByEmail(email);
        if (account == null) {
            throw new UnauthorizedException();
        }
        accountService.completeSignUp(account, token);

        String jwtToken = jwt.createToken(
                account.getId(),
                account.getName(), account.getEmail(), account.getRole().toString());
        jwt.getAuthentication(jwtToken);
        AccountDto accountDto = AccountDto.of(account);

        return ApiResponseDto.createOK(
                new AuthenticationResponse(jwtToken, accountDto)
        );
    }
}
