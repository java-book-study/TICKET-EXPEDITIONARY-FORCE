package com.ticket.captain.account.authentication;

import com.ticket.captain.account.AccountApiController;
import com.ticket.captain.account.AccountSignupController;
import com.ticket.captain.account.dto.AccountDto;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class AuthenticationResource extends EntityModel<AuthenticationResponse> {

    public static EntityModel<AuthenticationResponse> of(AuthenticationResponse authenticationResponse) {
        return EntityModel.of(authenticationResponse).add(linkTo(AccountSignupController.class).slash(authenticationResponse.getAccountDto().getId()).withSelfRel());
    }

    public static EntityModel<AuthenticationResponse> loginOf(AuthenticationResponse authenticationResponse) {
        return EntityModel.of(authenticationResponse).add(linkTo(AuthenticationController.class).slash(authenticationResponse.getAccountDto().getId()).withSelfRel());
    }

    public static EntityModel<AuthenticationResponse> signUpOf(AuthenticationResponse authenticationResponse) {
        return EntityModel.of(authenticationResponse).add(linkTo(AccountSignupController.class).slash(authenticationResponse.getAccountDto().getId()).withSelfRel());
    }
}
