package org.example.projekt2_gruppe5.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Wishlist {
    int id;
    String name;
    LocalDate expirationDate;
    ArrayList<Wish> wishlist;

    //Contructers

    public Wishlist(String name) {
        this.name = name;
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

    public ArrayList<Wish> getWishlist() {
        return wishlist;
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
}
