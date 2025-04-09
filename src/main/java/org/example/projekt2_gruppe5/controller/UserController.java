package org.example.projekt2_gruppe5.controller;

import org.example.projekt2_gruppe5.exceptions.UserNotCreatedException;
import org.example.projekt2_gruppe5.model.User;
import org.example.projekt2_gruppe5.repository.UserRepo;
import org.example.projekt2_gruppe5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

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

        //First, format username to be lowercase
        userName = userName.toLowerCase();
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
        User user = new User(firstName, lastName, userName, passWord);

        try {
            userRepo.saveNewUser(user);
        }
        catch (UserNotCreatedException e){
            e.printStackTrace();

            redirectAttributes.addFlashAttribute("errorMessage", "Your user could not be created for an unknown reason. Try again with other inputs");
            return "redirect:/getCreateUser";
        }

        redirectAttributes.addFlashAttribute("userCreatedMessage", "User " + userName + " was created! Have fun wishing <3");
        return "redirect:/";
    }

    @GetMapping("/getCurrentUser")
    public String viewCurrentuser(Model model){
        User currentUser = userRepo.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "loginTest";
    }
}
