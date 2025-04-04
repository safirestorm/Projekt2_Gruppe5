package org.example.projekt2_gruppe5.controller;

import org.example.projekt2_gruppe5.service.LoginService;
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
    private LoginService loginService;

    @GetMapping("/")
    public String getLoginPage() {
        return "index";
    }

    // The model holds a key with a value - fx key "message" has data "Login succesful" and can be refered to with thymeleaf
    @PostMapping("/")
    public String login(String username, String password, RedirectAttributes redirectAttributes) {
        System.out.println("Arrived at login attempt");
        boolean success = loginService.login(username, password);

        if (success) {
            redirectAttributes.addFlashAttribute("loginMessage", "Welcome " + username);
            return "redirect:/getUserPage";
        } else {
            redirectAttributes.addFlashAttribute("loginError", "Fuck u");
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
        loginService.logout();
        return "redirect:/";
    }
}
