package com.example.graduationproject.Model;

import java.util.ArrayList;

public class Favorite2 {
    String name;
    String description;
    String price;
    String category;
    String image;
    String productiveFamilyId;
    String productiveFamilyName;
    ArrayList<String> ImageUrls;
    String user;
    String id;

    public Favorite2() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getProductiveFamilyId() {
        return productiveFamilyId;
    }

    public void setProductiveFamilyId(String productiveFamilyId) {
        this.productiveFamilyId = productiveFamilyId;
    }

    public String getProductiveFamilyName() {
        return productiveFamilyName;
    }

    public void setProductiveFamilyName(String productiveFamilyName) {
        this.productiveFamilyName = productiveFamilyName;
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
}
