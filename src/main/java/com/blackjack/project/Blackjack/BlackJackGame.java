package com.blackjack.project.Blackjack;


import com.blackjack.project.User.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.blackjack.project.Blackjack.Suit.HEART;
import static com.blackjack.project.Blackjack.Suit.SPADE;


public class BlackJackGame {

    private Shoe shoe;
    private Hand dealersHand;
    private List<Hand> playersHands;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //todo: fix this so dealer's strategy can be injected
    @Autowired
    private DealerHandResolver dealerHandResolver;

    public BlackJackGame(double bet) {
        this.shoe = new Shoe(6);

        dealersHand = new Hand(bet);
        dealersHand.addCard(shoe.draw());

        Card card1 = new Card(SPADE, Value.ACE);
        Card card2 = new Card(HEART, Value.ACE);

        Hand playersHand = new Hand(bet);
        playersHand.setActive(true);
        playersHands = new ArrayList<Hand>();
        playersHands.add(playersHand);
        for (int i = 0; i< playersHands.size(); i++){
//            playersHands.get(i).addCard(shoe.draw());
//            playersHands.get(i).addCard(shoe.draw());
            playersHands.get(i).addCard(card1);
            playersHands.get(i).addCard(card2);
        }

    }


    public Hand getDealersHand() {
        return dealersHand;
    }

    public void setDealersHand(Hand hand) {
        this.dealersHand = hand;
    }

    public boolean isPair(Hand hand){
        if (hand.getCards().get(0).getIntValue() == hand.getCards().get(1).getIntValue() && hand.getCards().size() == 2) return true;
        return false;
    }

    public void playerSplit(Hand hand){
            Hand newHand = new Hand(hand.getBet());
            Hand newHand1 = new Hand(hand.getBet());
            newHand.addCard(hand.getCards().get(1));
            newHand1.addCard(hand.getCards().get(0));
            newHand1.setActive(true);


            Card card1 = new Card(Suit.DIAMOND, Value.ACE);
            newHand1.addCard(card1);

            playersHands.remove(hand);
            playersHands.add(newHand1);
            playersHands.add(newHand);

    }

    public void playerHit(Hand hand) {
        hand.addCard(shoe.draw());
    }

    public void dealerHit() {
        dealersHand.addCard(shoe.draw());
    }

    public boolean playerDoneAllHands() {
        for (Hand hand : getPlayersHands()) if (!hand.isFinished()) return false;
        return true;
    }

    public boolean playerBustedAllHands() {
        for (Hand hand : getPlayersHands()) if (!handBusted(hand)) return false;
        return true;
    }

    public boolean handBusted(Hand hand) {
        boolean allTotalsBusted = true;
        for (Integer totals : hand.getTotals()) {
            if (totals <= 21) {
                allTotalsBusted = false;
            }
        }
        return allTotalsBusted;
    }

    //Return false if any total is less than 21
    public boolean dealerBusted() {
        for (Integer totals : dealersHand.getTotals()) {
            if (totals <= 21) {
                return false;
            }
        }
        return true;
    }

    public void resolveDealerHand() {
        while (!dealerBusted() && dealerBelowSeventeen()) {
            dealerHit();
        }
    }

    //If any total is >= 17 but not a bust do no hit
    private boolean dealerBelowSeventeen() {
        for (Integer totals : getDealersHand().getTotals()) {
            if (totals >= 17 && totals < 22) { //Do not hit if dealers has 17 or greater
                return false;
            }
        }
        return true;
    }

    //method to update player's play money
    public void deductBet(double bet) {
        user.setCoinAmount(user.getCoinAmount() - bet);
    }

    public List<Hand> getPlayersHands() {
        return playersHands;
    }

    public void setPlayersHands(List<Hand> playersHands) {
        this.playersHands = playersHands;
    }

    public String result(Hand hand) {
        if (handBusted(hand) || dealersHand.blackJack() && !hand.blackJack()) {
            return "You lost!";
        } else if (hand.blackJack() && !dealersHand.blackJack() ) {
            return "Blackjack! You win!";
        } else if (hand.finalTotal().equals(dealersHand.finalTotal())) {
            return "Push";
        } else if (hand.blackJack() || (dealersHand.blackJack())) {
            return "Push";
        } else if ((hand.finalTotal() < dealersHand.finalTotal()) && (dealersHand.finalTotal() < 22)) {
            return "You lost!";
        } else {
            return "You win!";
        }
    }
    public double resolveWinnings(){
        double totalWinnings = 0;
        for (Hand hand : getPlayersHands()){
            if (result(hand).equals("You lost!")) {
            } else if (result(hand).equals("You win!")){
                user.setCoinAmount(user.getCoinAmount()+(hand.getBet()*2));
                totalWinnings = totalWinnings + hand.getBet();
            } else if (result(hand).equals("Blackjack! You win!")){
                user.setCoinAmount(user.getCoinAmount()+(hand.getBet()*2.5));
                totalWinnings = totalWinnings + hand.getBet()*1.5;
            } else if (result(hand).equals("Push")){
                user.setCoinAmount(user.getCoinAmount()+hand.getBet());
            }
        }
        return totalWinnings;
    }

}
