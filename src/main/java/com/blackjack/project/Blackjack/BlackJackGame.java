package com.blackjack.project.Blackjack;


import com.blackjack.project.User.User;
import org.springframework.beans.factory.annotation.Autowired;


public class BlackJackGame {

    private Shoe shoe;
    private Hand dealersHand;
    private Hand playersHand;
    //private User playerBet;
    private int betAmount;
    private User user;

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //todo: fix this so dealer's strategy can be injected
    @Autowired
    private DealerHandResolver dealerHandResolver;

    public BlackJackGame() {
        this.shoe = new Shoe(6);

        dealersHand = new Hand();
        dealersHand.addCard(shoe.draw());

        playersHand = new Hand();
        playersHand.addCard(shoe.draw());
        playersHand.addCard(shoe.draw());

        //include player´s bet each time a new game starts
        //playerBet = new User();
        //playerBet.getBetAmount();

    }


    public Hand getDealersHand() {
        return dealersHand;
    }

    public Hand getPlayersHand() {
        return playersHand;
    }

    public void setPlayersHand(Hand hand) {
        this.playersHand = hand;
    }

    public void setDealersHand(Hand hand) {
        this.dealersHand = hand;
    }

    public void playerHit() {
        playersHand.addCard(shoe.draw());
    }

    public void dealerHit() {
        dealersHand.addCard(shoe.draw());
    }

    public int doubleUp() {
        playerHit();
        betAmount *= 2;
        return betAmount;
    }


    public boolean playerBusted() {
        boolean allTotalsBusted = true;
        for (Integer totals : playersHand.getTotals()) {
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
    public void updateAmount() {
        user.setCoinAmount(user.getCoinAmount() - betAmount);
    }


    public String result() {
        if (playerBusted() || dealersHand.blackJack()) {
            return "You lose! :(";
        } else if (playersHand.blackJack()) {
            user.setCoinAmount(user.getCoinAmount() + (int) Math.round(2.5 * betAmount));
            return "You win!";
        } else if (playersHand.finalTotal().equals(dealersHand.finalTotal())) {
            user.setCoinAmount(user.getCoinAmount() + betAmount);
            return "Push";
        } else if ((playersHand.finalTotal() < dealersHand.finalTotal()) && (dealersHand.finalTotal() < 22)) {
            return "You lose! :(";
        } else {
            user.setCoinAmount(user.getCoinAmount() + 2*betAmount);
            return "You win!";
        }
    }
}
