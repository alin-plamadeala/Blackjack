package com.blackjack.project.Register;

import com.blackjack.project.User.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RegisterRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insert(User user) {
        this.entityManager.persist(user);
    }

}
