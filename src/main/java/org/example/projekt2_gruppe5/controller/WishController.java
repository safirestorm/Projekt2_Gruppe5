package org.example.projekt2_gruppe5.controller;

import org.example.projekt2_gruppe5.model.Wish;
import org.example.projekt2_gruppe5.repository.WishRepo;
import org.example.projekt2_gruppe5.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class WishController {

    @Autowired
    private WishRepo wishRepo;

    @Autowired
    private WishService wishService;

@GetMapping("/createWishOnWishlist")
    public String createWish(@RequestParam("id") int wishlistId, Model model){
    model.addAttribute("wishlistId", wishlistId);
    return "createWish";
}

@PostMapping("/saveCreateWish")
    public String postCreateWish(
        @RequestParam("name") String name,
        @RequestParam("price") int price,
        @RequestParam("link") String link,
        @RequestParam("description") String description,
        @RequestParam("wishlistId") int wishlistId){

       String image = wishService.getImage();

       Wish wish = new Wish(name, price, link, description, image);
       wishRepo.saveWish(wish, wishlistId);
       return "redirect:/wishlist/" + wishlistId;
}

@GetMapping("/wishlist/{id}")
    public String viewWishlist(@PathVariable("id") int wishlistId, Model model){
    ArrayList<Wish> wishList = wishRepo.getAllWishesOnWishlist(wishlistId);
    model.addAttribute("wishList", wishList);
    model.addAttribute("wishlistId", wishlistId);
    //if !userOfList==currentuser: Model addattribute showReservedStatus
    return "viewWishlist";
}

@PostMapping("/deleteWish")
    public String deleteWishFromWishlist(@RequestParam("id") int id, @RequestParam("wishlistId") int wishlistId){
    wishRepo.deleteWish(id);
    return "redirect:/wishlist/" + wishlistId;
}

@PostMapping("/reserveOrUnreserveWish")
    public String reserveWish(@PathVariable("id") int wishlistId, @PathVariable("wishID") int wishID){

    //wishRepo.switchReservedStatus(wishID)

    return "redirect:/wishlist/" + wishlistId;
}

@GetMapping("/getUpdateWish")
    public String updateWish(@RequestParam("id") int id, Model model){
    Wish wish = wishRepo.getWishById(id);
    model.addAttribute("wish", wish);
    model.addAttribute("wishlistId", wish.getWishlistId());
    return "updateWish";
}

@PostMapping("/saveUpdateWish")
    public String postUpdateWish(
            @RequestParam("id") int id,
            @RequestParam("wishlistId") int wishlistId,
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("link") String link,
            @RequestParam("description") String description){

    String image = wishService.getImage();

    Wish wish = new Wish(id, name, price, link, description, image);
    wishRepo.updateWish(wish);
    return "redirect:/wishlist/" + wishlistId;
}
}
