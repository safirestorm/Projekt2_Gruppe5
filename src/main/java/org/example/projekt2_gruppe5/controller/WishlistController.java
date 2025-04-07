package org.example.projekt2_gruppe5.controller;

import org.example.projekt2_gruppe5.repository.UserRepo;
import org.example.projekt2_gruppe5.repository.WishlistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WishlistController {

    @Autowired
    private WishlistRepo wishlistRepository;

    @GetMapping("/getUserPage")
    public String showWishlists(Model model) {
        // får WishLists
        System.out.println("Vi prøver at vise wishlists");
        model.addAttribute("wishListList", wishlistRepository.getAllWishlist());
        System.out.println("Wishlists: " + wishlistRepository.getAllWishlist());
        return "brugerside"; // Thymeleaf will look for this .html template
    }

    @GetMapping("/getWishlistPage")
    public String getWishlistId(@PathVariable int wishListId, Model model) {

        int wishlistId = wishListId;

        return "viewWishlist";
    }
}
