package org.example.projekt2_gruppe5.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Wishlist {
    int id;
    String name;
    String description;
    LocalDate expirationDate;
    ArrayList<Wish> wishlist;
    String userId;

    //Contructers

    public Wishlist(String name) {
        this.name = name;
    }
    public Wishlist(){}

    public Wishlist(String name, LocalDate expirationDate, String description) {
        this.name = name;
        this.expirationDate = expirationDate;
        this.description = description;
    }

    //Getters
    private int getId() {
        return id;
    }

    private String getName() {
        return name;
    }

    private LocalDate getExpirationDate() {
        return expirationDate;
    }

    private ArrayList<Wish> getWishlist() {
        return wishlist;
    }
    private String getDescription() {
        return description;
    }
    private String getUserId() {
        return userId;
    }
    //Setters
    private void setId(int id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    private void setWishlist(ArrayList<Wish> wishlist) {
        this.wishlist = wishlist;
    }
    private void setUserId(String userId) {
        this.userId = userId;
    }
    private void setDescription(String description) {
        this.description = description;
    }
}
