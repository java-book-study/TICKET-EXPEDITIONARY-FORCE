package com.ticket.captain.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping("/login")
    public String login (Model model){
        model.addAttribute("name", "sonnie");
        return "account/sign-up";
    }
}
