package com.blackjack.project.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public void save(Card card){
        cardRepository.save(card);
    }

    public List<Card> findAll(){
        return cardRepository.findAll();
    }

    public Card findById(int id){
        return cardRepository.findById(id);
    }

}
