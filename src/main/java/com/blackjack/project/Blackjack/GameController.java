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
    public String startNewGame(Model model, HttpSession session, @RequestParam("amount") int amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        //evaluate if bet amount is not higher than start coins
        model.addAttribute("validate", false);
        if (amount > user.getCoinAmount()) {
            model.addAttribute("validate", true);
            return "game/mainMenu";
        }else {
            BlackJackGame game = new BlackJackGame(amount);
            game.setUser(user);
            session.setAttribute("game", game);
            game.deductBet(amount);
            userRepository.save(game.getUser());

            int index = 0;
            for (Hand hand : game.getPlayersHands()){
                if (hand.isPair()){
                    model.addAttribute("handIndex", index);
                    model.addAttribute("split", true);
                }
                index++;
            }
            return "game/inProgress";
        }
    }


    @RequestMapping(method = RequestMethod.POST, params = "hit")
    public String hit(HttpSession session, @RequestParam("hand") int handIndex) {
        BlackJackGame game = (BlackJackGame) session.getAttribute("game");
        Hand hand = game.getPlayersHands().get(handIndex);
        game.playerHit(hand);
        if (game.playerBustedAllHands() || game.playerDoneAllHands()) {
            game.resolveDealerHand();
            return "game/endGame";
        } else {
            return "game/inProgress";
        }
    }

    @RequestMapping(method = RequestMethod.POST, params = "stand")
    public String stand(HttpSession session, @RequestParam("hand") int handIndex) {
        BlackJackGame game = (BlackJackGame) session.getAttribute("game");
        game.getPlayersHands().get(handIndex).setActive(false);
        game.getPlayersHands().get(handIndex).setFinished(true);

        if (game.getPlayersHands().size() > handIndex+1){
            game.getPlayersHands().get(handIndex+1).setActive(true);
        }else if (game.getPlayersHands().size() == handIndex+1){
            for (int i = 0; i < game.getPlayersHands().size(); i++){
                if (!game.getPlayersHands().get(i).isFinished()){
                    game.getPlayersHands().get(i).setActive(true);
                    break;
                }
            }
        }

        if (game.playerBustedAllHands() || game.playerDoneAllHands()) {
            game.resolveDealerHand();
            return "game/endGame";
        } else {
            return "game/inProgress";
        }
    }

    @RequestMapping(method = RequestMethod.POST, params = "split")
    public String split(HttpSession session, Model model, @RequestParam("hand") int handIndex) {
        BlackJackGame game = (BlackJackGame) session.getAttribute("game");
        model.addAttribute("validate", false);
        if (game.getPlayersHands().get(0).getBet() > game.getUser().getCoinAmount()) {
            model.addAttribute("validate", true);
            return "game/inProgress";
        }else {
            game.deductBet(game.getPlayersHands().get(handIndex).getBet());
            userRepository.save(game.getUser());

            game.playerSplit(game.getPlayersHands().get(handIndex));
            int index = 0;
            for (Hand hand : game.getPlayersHands()){
                if (hand.isPair()){
                    model.addAttribute("handIndex", index);
                    model.addAttribute("split", true);
                }
                index++;
            }
            return "game/inProgress";
        }
    }

    @RequestMapping(method = RequestMethod.POST, params = "finish")
    public String finish(HttpSession session, SessionStatus status, Model model) {
        BlackJackGame game = (BlackJackGame) session.getAttribute("game");

        User user = game.getUser();
        userRepository.save(user);
        model.addAttribute("totalWinnings", game.resolveWinnings());
        status.setComplete();
        return "game/results";
    }
}