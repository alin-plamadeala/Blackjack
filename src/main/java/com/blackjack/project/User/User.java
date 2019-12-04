package com.blackjack.project.User;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String password;

    @Transient
    private String passwordConfirm;

    private String firstName;

    private String lastName;

    @Column(name = "birthday")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String email;

    //initial coin amount
    @Transient
    private int startCoinAmount=100;

    //final coin amount when game is over
    private int finalCoinAmount;

    //coin amount player wishes to bet
    private int betAmount;

    public int getFinalCoinAmount() {
        return finalCoinAmount;
    }

    public void setFinalCoinAmount(int finalCoinAmount) {
        this.finalCoinAmount = finalCoinAmount;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }
    //    private final Set<GrantedAuthority> authorities = new HashSet<>();


    public User() {
        //authorities.add(new SimpleGrantedAuthority("USER"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStartCoinAmount() {
        return startCoinAmount;
    }

    public void setStartCoinAmount(int startCoinAmount) {
        this.startCoinAmount = startCoinAmount;
    }
}
