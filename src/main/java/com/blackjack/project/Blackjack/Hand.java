package com.blackjack.project.Blackjack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hand {

    private boolean active = false;
    private boolean finished = false;
    private double bet;
    private List<Card> cards;
    private List<Integer> totals;

    public Hand(double bet) {
        this.bet = bet;
        totals = new ArrayList<Integer>();
        totals.add(0);

        cards = new ArrayList<Card>();
    }

    public void addCard(Card card) {
        cards.add(card);

        if (card.getIntValue().equals(Value.ACE)) {
            //increment each total by value
            List<Integer> newTotals = new ArrayList<Integer>();
            for (Integer oldTotal : totals) {
                newTotals.add(oldTotal + 1);
                newTotals.add(oldTotal + 11);
            }
            totals = newTotals;
        } else {
            //increment each total by value
            List<Integer> newTotals = new ArrayList<Integer>();
            for (Integer oldTotal : totals) {
                newTotals.add(oldTotal + card.getIntValue().value());
            }
            totals = newTotals;
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card.toString());
        }
        return sb.toString();
    }

    public boolean isPair(){
        if(size()==2){
            if (getCards().get(0).getIntValue() == getCards().get(1).getIntValue() && getCards().size() == 2) return true;
        }
        return false;
    }

    public int size() {
        return cards.size();
    }

    public List<Integer> getTotals() {
        return totals;
    }

    public String result() {
        return blackJack() ? "Blackjack!" : finalTotal() > 21 ? "Busted!" : "";
    }

    public boolean blackJack() {
        return finalTotal().equals(21) && size() == 2;
    }

    //Largest total less than 21 or smallest total if over 21..
    public Integer finalTotal() {
        Integer currFinalTotal = totals.get(0);
        for (Integer total : totals) {
            if ((total > currFinalTotal && total <= 21) || ((total < currFinalTotal) && bothGreaterThanTwentyOne(total, currFinalTotal))) {
                currFinalTotal = total;
            }
        }
        return currFinalTotal;
    }

    private boolean bothGreaterThanTwentyOne(Integer total, int finalTotal) {
        return (total > 21 && finalTotal > 21);
    }

    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
