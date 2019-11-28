package com.blackjack.project.card;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface CardRepository extends Repository<Card, Long> {

    List<Card> findAll();

    Card findById(int id);

    void save(Card card);

}
