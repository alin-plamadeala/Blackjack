package com.blackjack.project.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CardController {

    @Autowired
    private CardService cardService;

    //show all cards
    @GetMapping("/cards")
    public String cards(Model model) {
        List<Card> cardsList = cardService.findAll();
        model.addAttribute("cardsList", cardsList);
        return "show-cards";
    }

    //deal a new card
    @GetMapping("/new")
    public String newCard(Model model, Card card){
        model.addAttribute("dealCard");
        return "deal-cards";
    }

}
