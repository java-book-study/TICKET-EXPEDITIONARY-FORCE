package com.ticket.captain.account;

import com.ticket.captain.account.dto.AccountCreateDto;
import com.ticket.captain.common.ErrorsResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountSignupController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @GetMapping
    public ModelAndView AccountCreateDto(){
        return new ModelAndView("account/sign-up", "accountCreateDto", new AccountCreateDto());
    }

    @PostMapping
    public ResponseEntity createAccount(@RequestBody @Valid AccountCreateDto accountCreateDto, Errors errors) throws URISyntaxException {
        if(errors.hasErrors()){
            return badRequest(errors);
        }
        Account account = accountService.createAccount(accountCreateDto);
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

        /**
         * completeSignUp 내부의 login 메서드 제외함. 해당 view 에서는 이메일 인증 완료의 여부에 대한 내용이 있어야 하며,
         * login을 할 수 있는 페이지로 갈 수 있어야함
         */
        accountService.completeSignUp(account);
        return view;
    }

    private ResponseEntity badRequest(Errors errors){

        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        AccountCreateDtoValidator validator=new AccountCreateDtoValidator(accountRepository);
        binder.addValidators(validator);
    }
}
