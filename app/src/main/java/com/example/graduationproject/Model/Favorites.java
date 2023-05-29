package com.example.graduationproject.Model;

public class Favorites {
    String name;
    String description;
    String price;
    String category;
    String image;
    String productiveFamilyId;
    String productiveFamilyName;
    String user;
    String id;


    public String getProductiveFamilyName() {
        return productiveFamilyName;
    }

    public void setProductiveFamilyName(String productiveFamilyName) {
        this.productiveFamilyName = productiveFamilyName;
    }

    public Favorites() {

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

    @Override
    public String toString() {
        return "Favorites{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' +
                ", productiveFamilyId='" + productiveFamilyId + '\'' +
                ", user='" + user + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getProductiveFamilyId() {
        return productiveFamilyId;
    }

    public void setProductiveFamilyId(String productiveFamilyId) {
        this.productiveFamilyId = productiveFamilyId;
    }

    public Favorites(String name, String description, String price, String category, String image, String productiveFamilyId, String user, String id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
        this.productiveFamilyId = productiveFamilyId;
        this.user = user;
        this.id = id;
    }
}
