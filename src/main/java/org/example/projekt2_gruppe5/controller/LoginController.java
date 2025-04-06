package org.example.projekt2_gruppe5.controller;

import jakarta.servlet.http.HttpSession;
import org.example.projekt2_gruppe5.model.User;
import org.example.projekt2_gruppe5.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    // Allows usage of the LoginService
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/")
    public String getLoginPage(HttpSession httpSession) {
        System.out.println(httpSession.getId());
        return "index";
    }

    // The model holds a key with a value - fx key "message" has data "Login succesful" and can be refered to with thymeleaf
    @PostMapping("/tryLoginUser")
    public String tryLoginUser(String username, String password, RedirectAttributes redirectAttributes, Model model) {
        System.out.println("Arrived at login attempt");
        boolean success = userRepo.login(username, password);

        if (success) {
            redirectAttributes.addFlashAttribute("loginMessage", "Welcome " + username);
            return "redirect:/getCurrentUser";
        } else {
            redirectAttributes.addFlashAttribute("loginError", "Could not log in, try again");
            return "redirect:/";
        }
    }

    @GetMapping("/getUserPage")
    public String getUserPage(Model model) {
        System.out.println("Arrived at getUserPage");
        return "brugerside";
    }

    @GetMapping("/logout")
    public String logout() {
        //loginService.logout();
        return "redirect:/";
    }
}
