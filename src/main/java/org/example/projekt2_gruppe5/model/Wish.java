package org.example.projekt2_gruppe5.model;

public class Wish {
    int wishId;
    String name;
    int price;
    String link;
    String image;

    public Wish(int wishId, String name, int price, String link, String image) {
        this.wishId = wishId;
        this.name = name;
        this.price = price;
        this.link = link;
        this.image = image;
    }

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


}
