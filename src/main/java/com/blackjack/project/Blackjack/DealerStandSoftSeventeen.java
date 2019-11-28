package com.blackjack.project.Blackjack;

public class DealerStandSoftSeventeen extends AbstractDealerHandResolver {

    public DealerStandSoftSeventeen(BlackJackGame blackJackGame) {
        super(blackJackGame);
    }

    public DealerStandSoftSeventeen() {
    }

    @Override
    public void resolveDealerHand() {
        while (dealerBelowSeventeen()) {
            blackJackGame.dealerHit();
        }
    }

    //Dealer should stand on soft seventeen because one total is >= 17..
    boolean dealerBelowSeventeen() {
        boolean seventeenOrHigherFound = false;
        for (Integer totals : blackJackGame.getDealersHand().getTotals()) {
            if (totals >= 17) {
                return true;
            }
        }
        return seventeenOrHigherFound;
    }
}