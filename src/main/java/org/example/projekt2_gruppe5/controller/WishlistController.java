package org.example.projekt2_gruppe5.controller;

import org.example.projekt2_gruppe5.model.User;
import org.example.projekt2_gruppe5.model.Wishlist;
import org.example.projekt2_gruppe5.repository.UserRepo;
import org.example.projekt2_gruppe5.repository.WishRepo;
import org.example.projekt2_gruppe5.repository.WishlistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class WishlistController {

    @Autowired
    private WishlistRepo wishlistRepository;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private WishRepo wishRepo;

    @GetMapping("/getUserPage")
    public String showWishlists(Model model) {

        User currentUser = userRepo.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        System.out.println("Current user: " + currentUser);
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
    public String deleteWishlist(int id){
        wishRepo.deleteAllWishesOnWishlist(id);
        wishlistRepository.deleteWishlist(id);
        return "redirect:/getUserPage";
    }
    @GetMapping("/getEditWishlist")
    public String showEditForm(@RequestParam int id, Model model) {
        Wishlist wishlist = wishlistRepository.getWishlistById(id);
        model.addAttribute("wishlist", wishlist);
        return "editWishlist";
    }
    @PostMapping("/saveEditWishlist")
    public String editCreateWishlist
            (@RequestParam("id") int id,
            @RequestParam("name") String name,
            @RequestParam("expirationDate") LocalDate date,
            @RequestParam("description") String description){
        System.out.println("vi modtager parametererne");
        Wishlist wishlist = wishlistRepository.getWishlistById(id);
        wishlist.setName(name);
        wishlist.setDescription(description);
        wishlist.setExpirationDate(date);

        System.out.println("prøver at gemme ænringerne i din wishlist");
        wishlistRepository.updateWishlist(wishlist);
        System.out.println("Wishlist er gemt");
        return "redirect:/getUserPage";
    }
}
