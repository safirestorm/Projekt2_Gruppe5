package org.example.projekt2_gruppe5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @GetMapping("/getCreateUser")
    public String getCreateUser(){
        System.out.println("Registered click on getCreateUser Button from index");
        return "createUser";
    }
}
