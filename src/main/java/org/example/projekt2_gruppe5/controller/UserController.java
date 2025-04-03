package org.example.projekt2_gruppe5.controller;

import org.example.projekt2_gruppe5.model.User;
import org.example.projekt2_gruppe5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getCreateUser")
    public String getCreateUser(){
        System.out.println("Registered click on getCreateUser Button from index");
        return "createUser";
    }

    @PostMapping("/saveCreateUser")
    public String saveUser(
            @RequestParam("userName") String userName,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("passWord") String passWord,
            @RequestParam("passWordControl") String passWordControl,
            RedirectAttributes redirectAttributes){
        System.out.println("It worked, read username as " + userName);

        //Check if username is taken, return error if it is
        if (userService.isUserNameAvailable(userName)){
            redirectAttributes.addFlashAttribute("errorMessage", "Username was taken, try again");
            return "redirect:/getCreateUser";
        }

        //Check if passwords match, if not, return error
        if (!passWord.equals(passWordControl)){
            redirectAttributes.addFlashAttribute("errorMessage", "Passwords did not match, try again");
            return "redirect:/getCreateUser";
        }

        //If all tests are passed, create the user
        User user = new User(userName, firstName, lastName, passWord);
        userService.saveNewUser(user);

        redirectAttributes.addFlashAttribute("userCreatedMessage", "User " + userName + " was created! Have fun wishing <3");
        return "redirect:/";
    }
}
