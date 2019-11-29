package com.blackjack.project.Blackjack;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("game")
@RequestMapping("/play")
public class GameController {
    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome() {
        return "game/mainMenu";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String startNewGame(HttpSession session){
        session.setAttribute("game", new BlackJackGame());
        return "game/inProgress";
    }

    @RequestMapping(method = RequestMethod.POST, params = "hit")
    public String hit(HttpSession session) {
        BlackJackGame game = (BlackJackGame) session.getAttribute("game");
        game.playerHit();
        if(game.playerBusted()){
            game.resolveDealerHand();
            return "game/endGame";
        }else{
            return "game/inProgress";
        }
    }

    @RequestMapping(method = RequestMethod.POST, params = "stand")
    public String stand(HttpSession session) {
        BlackJackGame game = (BlackJackGame) session.getAttribute("game");
        game.resolveDealerHand();
        return "game/endGame";
    }
    @RequestMapping(method = RequestMethod.POST, params = "finish")
    public String finish(HttpSession session, SessionStatus status) {
        status.setComplete();
        return "game/mainMenu";
    }


}