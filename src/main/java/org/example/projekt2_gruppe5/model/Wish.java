package org.example.projekt2_gruppe5.model;

public class Wish {
    private int wishId;
    private String name;
    private int price;
    private String link;
    private String description;
    private String image;
    private boolean isReserved;
    int wishlistId;

    public Wish(String name, int price, String link, String description, String image) {
        this.name = name;
        this.price = price;
        this.link = link;
        this.description = description;
        this.image = image;
        isReserved = false;
    }

    public Wish() {}

    public int getWishId() {
        return wishId;
    }

    public void setWishId(int wishId) {
        this.wishId = wishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }
}
