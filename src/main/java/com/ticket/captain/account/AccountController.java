package com.ticket.captain.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.net.URISyntaxException;


@Controller
@RequiredArgsConstructor
@RequestMapping(value="/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @GetMapping
    public ModelAndView signUpForm(){
        return new ModelAndView("account/sign-up", "signUpForm", new SignUpForm());
    }

    @PostMapping
    public ResponseEntity createAccount(@RequestBody SignUpForm signUpForm, Errors errors) throws URISyntaxException {
        if(errors.hasErrors()){
            return ResponseEntity.unprocessableEntity().body(signUpForm);
        }
        Account account = accountService.createAccount(signUpForm);
        accountService.login(account);
        URI createdUri = new URI("/");
        return ResponseEntity.created(createdUri).body(account);
    }

    @GetMapping("/check-email-token")
    public ResponseEntity checkEmailToken(@RequestBody String token, String email) throws URISyntaxException {
        Account account = accountRepository.findByEmail(email);
        if(account == null){
            return ResponseEntity.notFound().build();
        }
        if(!account.isValidToken(token)){
            return ResponseEntity.badRequest().body("this is not valid token");
        }

        accountService.completeSignUp(account);

        URI checkEmailUri = new URI("mail/check-email");
        return ResponseEntity.created(checkEmailUri).body(account);
    }

}
