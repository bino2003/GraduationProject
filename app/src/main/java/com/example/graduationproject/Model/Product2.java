package com.example.graduationproject.Model;

import java.util.ArrayList;

public class Product2 {
    String name;
    String productive_family;
    String description;
    String price;
    String category;
    String image;
    ArrayList<String> ImageUrls;
    String user;
    String id;
    String favid;

    public Product2() {
    }

    public Product2(String name,  String description,String price, String category, String productive_family,String user, ArrayList<String> imageUrls) {
        this.name = name;
        this.productive_family = productive_family;
        this.description = description;
        this.price = price;
        this.category = category;
        ImageUrls = imageUrls;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductive_family() {
        return productive_family;
    }

    public void setProductive_family(String productive_family) {
        this.productive_family = productive_family;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getImageUrls() {
        return ImageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        ImageUrls = imageUrls;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFavid() {
        return favid;
    }

    public void setFavid(String favid) {
        this.favid = favid;
    }
}
