package org.example.projekt2_gruppe5.controller;

import org.example.projekt2_gruppe5.model.Wish;
import org.example.projekt2_gruppe5.model.Wishlist;
import org.example.projekt2_gruppe5.repository.UserRepo;
import org.example.projekt2_gruppe5.repository.WishlistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class WishlistController {

    @Autowired
    private WishlistRepo wishlistRepository;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/getUserPage")
    public String showWishlists(Model model) {
        System.out.println("Vi prøver at vise wishlists");

        model.addAttribute("wishListList", wishlistRepository.getAllWishlist());

        System.out.println("Wishlists: " + wishlistRepository.getAllWishlist());

        return "brugerside"; // Thymeleaf will look for the 'brugerside.html' template
    }
    @GetMapping("/getCreateWishlist")
    public String showCreateWishlist() {
        return "createWishlist";
    }
    @PostMapping("/saveCreateWishlist")
    public String postCreateWishlist(
            @RequestParam("name") String name,
            @RequestParam("expirationDate") LocalDate date,
            @RequestParam("description") String description){
        System.out.println("vi modtager parametererne");
        Wishlist wishlist = new Wishlist(name, date, description);

        wishlist.setUserId(userRepo.getCurrentUser().getUsername());


        System.out.println("prøver at gemme den nye wishlist");
        wishlistRepository.saveWishlist(wishlist);
        System.out.println("Wishlist er gemt");
        return "redirect:/getUserPage";
    }
    @PostMapping("deleteWishlist")
    public String deleteWish(int id){
        wishlistRepository.deleteWishlist(id);
        return "redirect:/getUserPage";
    }
}
