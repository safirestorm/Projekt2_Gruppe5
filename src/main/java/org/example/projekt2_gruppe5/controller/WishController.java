package org.example.projekt2_gruppe5.controller;

import org.example.projekt2_gruppe5.model.Wish;
import org.example.projekt2_gruppe5.repository.WishRepo;
import org.example.projekt2_gruppe5.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WishController {

    @Autowired
    WishRepo wishRepo;

    @Autowired
    WishService wishService;

@GetMapping("/createWish")
    public String createWish(){
    return "createWish";
}

@PostMapping("/saveCreateWish")
    public String postCreateWish(
        @RequestParam("name") String name,
        @RequestParam("pris") int price,
        @RequestParam("link") String link,
        @RequestParam("description") String description){

       String image = wishService.getImage();

       Wish wish = new Wish(name, price, link, description, image);
       wishRepo.saveWish(wish);
       return "redirect:/viewWishlist";
}
}
