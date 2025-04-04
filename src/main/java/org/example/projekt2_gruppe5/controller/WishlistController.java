package org.example.projekt2_gruppe5.controller;

import org.example.projekt2_gruppe5.repository.WishlistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WishlistController {

    @Autowired
    private WishlistRepo wishlistRepository;

    @GetMapping("/wishlists")
    public String showWishlists(Model model) {
        // får WishLists
        model.addAttribute("wishListList", wishlistRepository.getAllWishlist());
        return "brugerside"; // Thymeleaf will look for this .html template
    }
}
