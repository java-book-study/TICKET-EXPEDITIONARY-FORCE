package com.ticket.captain.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sign-up")
public class AccountController {

    @GetMapping
    public String login (Model model){
        model.addAttribute(new SignUpForm());
        return "account/sign-up";
    }
}
