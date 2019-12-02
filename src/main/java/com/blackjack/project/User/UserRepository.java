package com.blackjack.project.User;

import org.springframework.data.repository.Repository;

import java.util.List;


public interface UserRepository extends Repository<User, Long> {

    User findByUsername(String username);
    User findById(int id);

    void save(User user);
}
