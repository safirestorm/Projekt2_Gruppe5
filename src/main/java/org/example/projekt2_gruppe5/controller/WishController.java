package org.example.projekt2_gruppe5.controller;

import org.example.projekt2_gruppe5.model.Wish;
import org.example.projekt2_gruppe5.repository.UserRepo;
import org.example.projekt2_gruppe5.repository.WishRepo;
import org.example.projekt2_gruppe5.repository.WishlistRepo;
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
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private WishlistRepo wishlistRepo;

    // Viser siden til at oprette et nyt ønske på en ønskeliste
    @GetMapping("/createWishOnWishlist")
    public String createWish(@RequestParam("id") int wishlistId, Model model){
    model.addAttribute("wishlistId", wishlistId);
    return "createWish";
}

    // Håndterer indsendelsen af formularen for at oprette et nyt ønske
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

    // Viser ønskerne på en ønskeliste
    @GetMapping("/wishlist/{id}")
        public String viewWishlist(@PathVariable("id") int wishlistId, Model model){
        ArrayList<Wish> wishList = wishRepo.getAllWishesOnWishlist(wishlistId);
        model.addAttribute("wishList", wishList);
        model.addAttribute("wishlistId", wishlistId);

        try {
            //Check om den nuværende bruger er ejer af ønsket
            if (userRepo.getCurrentUser().getUsername().equalsIgnoreCase(wishlistRepo.getOwnerUserName(wishlistId))) {
                System.out.println("Owner of wishlist is: " + wishlistRepo.getOwnerUserName(wishlistId));
                System.out.println("Current user is: " + userRepo.getCurrentUser().getUsername());
                //Hvis brugeren ejer ønsket, så skal reservations-status gemmes væk
                model.addAttribute("hideReservedStatus", true);
                //Eftersom brugeren er ejeren, skal dette også sendes til siden, således at rediger og slet knapper kan vises
                model.addAttribute("isOwner", true);

            }
        }
        catch (NullPointerException e){
            System.out.println("No user is logged in");

        }

        return "viewWishlist";
    }

    // Sletter et ønske fra en ønskeliste
    @PostMapping("/deleteWish")
        public String deleteWishFromWishlist(@RequestParam("id") int id, @RequestParam("wishlistId") int wishlistId){
        wishRepo.deleteWish(id);
        return "redirect:/wishlist/" + wishlistId;
    }

    // Skifter mellem at reservere og ophæve reservation af et ønske
    @PostMapping("/reserveOrUnreserveWish/{id}gift{wishID}")
        public String reserveWish(@PathVariable("id") int wishlistId, @PathVariable("wishID") int wishID){

        System.out.println("Prøver at ændre reservations status på ønske");
        wishRepo.switchReservedStatus(wishID);
        System.out.println("Har ændret status på ønske");

        return "redirect:/wishlist/" + wishlistId;
    }

    // Viser siden til at redigere et eksisterende ønske
    @GetMapping("/getUpdateWish")
        public String updateWish(@RequestParam("id") int id, Model model){
        Wish wish = wishRepo.getWishById(id);
        model.addAttribute("wish", wish);
        model.addAttribute("wishlistId", wish.getWishlistId());
        return "updateWish";
    }

    // Håndterer indsendelsen af formularen til at opdatere et ønske
    @PostMapping("/saveUpdateWish")
        public String postUpdateWish(
                @RequestParam("id") int id,
                @RequestParam("wishlistId") int wishlistId,
                @RequestParam("name") String name,
                @RequestParam("price") int price,
                @RequestParam("link") String link,
                @RequestParam("description") String description){

        Wish wish = wishRepo.getWishById(id);
        wish.setName(name);
        wish.setPrice(price);
        wish.setLink(link);
        wish.setDescription(description);
        wishRepo.updateWish(wish);
        return "redirect:/wishlist/" + wish.getWishlistId();
    }

    // Redirecter brugeren til linket knyttet til et ønske
    @GetMapping("/GoToLink")
        public String goToGift(@RequestParam("id") int id){
        Wish wish = wishRepo.getWishById(id);
        String link = wish.getLink();
        System.out.println("Link: "+link);
        return "redirect:" + link;
    }
}
