package com.ticket.captain.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import javax.validation.Valid;
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
    public ResponseEntity createAccount(@RequestBody @Valid SignUpForm signUpForm, Errors errors) throws URISyntaxException {
        if(errors.hasErrors()){
            errors.setNestedPath("/");
            return badRequest(errors);
        }
        Account account = accountService.createAccount(signUpForm);
        accountService.login(account);
        return ResponseEntity.created(new URI("/sign-up/complete")).body(account);
    }

    @GetMapping("/complete")
    public ModelAndView completeSignUp(@RequestBody Account account) {
        return new ModelAndView("account/complete", "account", account);
    }


    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model) {
        Account account = accountRepository.findByEmail(email);
        String view = "account/checked-email";

        if(account == null){
            model.addAttribute("error", "wrong.email");
            return view;
        }
        if(!account.isValidToken(token)){
            model.addAttribute("error", "wrong.token");
            return view;
        }

        accountService.completeSignUp(account);
        return view;
    }

    private ResponseEntity badRequest(Errors errors) throws URISyntaxException {

        return ResponseEntity.badRequest().body(errors);
    }
}
