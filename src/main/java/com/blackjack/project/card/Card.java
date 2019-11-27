package com.blackjack.project.card;
import com.blackjack.project.deck.Deck;

import javax.persistence.*;

@Entity
@Table(name="cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CardType type;

    @Enumerated(EnumType.STRING)
    private CardValue value;

    private String img;

    @ManyToOne
    @JoinColumn(name="deck_id", nullable = false)
    private Deck deck;

    public long getId() {
        return id;
    }

    public Card(CardType type, CardValue value, String img) {
        this.type = type;
        this.value = value;
        this.img = img;
    }

    public Card() {
    }

    public CardType getType() {
        return type;
    }

    public CardValue getValue() {
        return value;
    }

    public String getImg() {
        return img;
    }
}
