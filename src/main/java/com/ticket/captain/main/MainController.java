package com.ticket.captain.main;

import com.ticket.captain.account.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(@CurrentUser Account account , Model model ){
        if(account != null){
            model.addAttribute(account);
        }
        return "index";
    }
}
