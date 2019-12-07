package com.blackjack.project.Blackjack;

import com.blackjack.project.User.Security.UserService;
import com.blackjack.project.User.User;
import com.blackjack.project.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@SessionAttributes("game")
@RequestMapping("/play")
public class GameController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome() {
        return "game/mainMenu";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String startNewGame(Model model, HttpSession session, @RequestParam("amount") int amount,
                               @RequestParam("coins")int coins, Principal principal) {
        BlackJackGame game = new BlackJackGame();
        User user = userService.findByUsername(principal.getName());
        game.setUser(user);
        user.setCoinAmount(coins);
        game.setBetAmount(amount);
        //evaluate if bet amount is not higher than start coins
        if (game.getBetAmount() > user.getCoinAmount()) {
            model.addAttribute("validate", "amount too high");
            return "game/mainMenu";
        }else {
            session.setAttribute("game", game);
            game.updateAmount();
            return "game/inProgress";
        }
    }


    @RequestMapping(method = RequestMethod.POST, params = "hit")
    public String hit(HttpSession session) {
        BlackJackGame game = (BlackJackGame) session.getAttribute("game");
        game.playerHit();
        if (game.playerBusted()) {
            game.resolveDealerHand();
            return "game/endGame";
        } else {
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
        BlackJackGame game = (BlackJackGame) session.getAttribute("game");
        User user = game.getUser();
        userRepository.save(user);
        status.setComplete();
        return "game/mainMenu";
    }
}