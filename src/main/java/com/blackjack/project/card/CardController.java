package com.blackjack.project.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    @GetMapping("/new/{id}")
    public String newCard(@PathVariable(name="id")int id, Model model){
        Card dealCard=cardService.findById(id);
        model.addAttribute("dealCard",dealCard);
        return "deal-cards";
    }

}
