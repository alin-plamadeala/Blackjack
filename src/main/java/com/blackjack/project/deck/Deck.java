package com.blackjack.project.deck;

import com.blackjack.project.card.Card;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="decks")
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "deck")
    private Set<Card> cards;

    public Deck() {
    }

    public Deck(Set<Card> cards) {
        this.cards = cards;
    }

    public long getId() {
        return id;
    }

    public Set<Card> getCards() {
        return cards;
    }



}
