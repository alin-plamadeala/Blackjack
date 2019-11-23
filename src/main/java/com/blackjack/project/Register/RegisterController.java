package com.blackjack.project.Register;

import com.blackjack.project.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired private RegisterRepository registerRepo;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("register", new User());
        return "register";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user) {
        registerRepo.insert(user);
        return "redirect:/register";
    }
}
