package com.blackjack.project;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="deck")
public class Deck {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany(mappedBy = "deck")
    private Set<Card> cards;

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
