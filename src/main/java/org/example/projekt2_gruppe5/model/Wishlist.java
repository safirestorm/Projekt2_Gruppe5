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
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    public String getStringExpirationDate(LocalDate expirationDate) {
        return expirationDate.toString();
    }

    public ArrayList<Wish> getWishlist() {
        return wishlist;
    }
    public String getDescription() {
        return description;
    }
    public String getUserId() {
        return userId;
    }
    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setWishlist(ArrayList<Wish> wishlist) {
        this.wishlist = wishlist;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
