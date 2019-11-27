package com.blackjack.project.card;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CardController {

    @GetMapping("/")
    public String cards() {
        //List<Account> listAccounts = service.listAll();
        //model.addAttribute("listAccounts", listAccounts);
        return "show-cards";
    }
}
