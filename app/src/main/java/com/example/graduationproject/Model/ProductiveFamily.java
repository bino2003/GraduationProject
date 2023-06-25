package com.example.graduationproject.Model;

import java.util.List;

public class ProductiveFamily {







    String name;
    String details;
    String latlong;
    String location;
    String productCategory;
    String rating;
    List<String> evaluation;
    int phone;
    String category;
    String image;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public ProductiveFamily(String name, String details, String latlong, String location, String productCategory, String rating, List<String> evaluation, int phone, String category, String image, String id) {
        this.name = name;
        this.details = details;
        this.latlong = latlong;
        this.location = location;
        this.productCategory = productCategory;
        this.rating = rating;
        this.evaluation = evaluation;
        this.phone = phone;
        this.category = category;
        this.image = image;
        this.id = id;
    }

    public ProductiveFamily(String name, String details, String latlong, String location, List<String> evaluation, int phone, String category, String image, String id) {
        this.name = name;
        this.details = details;
        this.latlong = latlong;
        this.location = location;
        this.evaluation = evaluation;
        this.phone = phone;
        this.category = category;
        this.image = image;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ProductiveFamily(String name, String details, String latlong, String location, int phone, String category, String image, String id) {
        this.name = name;
        this.details = details;
        this.latlong = latlong;
        this.location = location;
        this.phone = phone;
        this.category = category;
        this.image = image;
        this.id = id;
    }

    public ProductiveFamily() {

    }

    @Override
    public String toString() {
        return "ProductiveFamily{" +
                "name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", latlong='" + latlong + '\'' +
                ", location='" + location + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", rating='" + rating + '\'' +
                ", evaluation=" + evaluation +
                ", phone=" + phone +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public List<String> getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(List<String> evaluation) {
        this.evaluation = evaluation;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ProductiveFamily(String name, String details, String location) {
        this.name = name;
        this.details = details;
        this.location = location;
    }

    public ProductiveFamily(String name, String details, String category, String image) {
        this.name = name;
        this.details = details;
        this.category = category;
        this.image = image;
    }


}

