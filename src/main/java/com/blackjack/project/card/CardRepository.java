package com.blackjack.project.card;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface CardRepository extends Repository<Card, Long> {

    List<Card> findAll();

    void save(Card card);

}
