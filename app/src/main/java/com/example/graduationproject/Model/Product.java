package com.example.graduationproject.Model;

import java.util.ArrayList;

public class Product {
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

    public Product(String name, String description, String price, String category, ArrayList<String> imageUrls) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        ImageUrls = imageUrls;
    }

    public String getProductive_family() {
        return productive_family;
    }

    public void setProductive_family(String productive_family) {
        this.productive_family = productive_family;
    }

    public String getFavid() {
        return favid;
    }

    public void setFavid(String favid) {
        this.favid = favid;
    }

    public Product(String name, String description, String price, String category, String image, String user, String id, String favid) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
        this.user = user;
        this.id = id;
        this.favid = favid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product(String name, String description, String price, String category, String image, String user, String id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
        this.user = user;
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Product(String name, String description, String price, String category, String image, String user) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
        this.user = user;
    }

    public Product(String name, String price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Product() {
    }


    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' +
                ", user='" + user + '\'' +
                ", id='" + id + '\'' +
                ", favid='" + favid + '\'' +
                '}';
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

    public Product(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public Product(String name, String description, String price, String category, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
    }
}
