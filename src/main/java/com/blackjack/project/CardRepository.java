package com.blackjack.project;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface CardRepository extends Repository<Card, Long> {

    List<Card> findAll();

    void save(Card card);

}
