package com.blackjack.project.Blackjack;

public abstract class AbstractDealerHandResolver implements DealerHandResolver {

    protected BlackJackGame blackJackGame;

    public AbstractDealerHandResolver(BlackJackGame blackJackGame) {
        this.blackJackGame = blackJackGame;
    }

    protected AbstractDealerHandResolver() {
    }
}
