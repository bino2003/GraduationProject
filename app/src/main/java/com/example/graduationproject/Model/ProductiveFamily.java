package com.example.graduationproject.Model;

import java.util.List;

public class ProductiveFamily {







    String name;
    String details;
    String latitude;
    String longitude;
    String productCategory;
    String rating;
    String location;
    List<String> evaluation;
    int phone;
    String category;
    String instgram;
    String Twitter;
    String image;
    String id;

    public String getInstgram() {
        return instgram;
    }

    public void setInstgram(String instgram) {
        this.instgram = instgram;
    }

    public String getTwitter() {
        return Twitter;
    }

    public void setTwitter(String twitter) {
        Twitter = twitter;
    }

    public ProductiveFamily(String name, String details, String latitude, String longitude, String productCategory, String rating, String location, List<String> evaluation, int phone, String category, String instgram, String twitter, String image, String id) {
        this.name = name;
        this.details = details;
        this.latitude = latitude;
        this.longitude = longitude;
        this.productCategory = productCategory;
        this.rating = rating;
        this.location = location;
        this.evaluation = evaluation;
        this.phone = phone;
        this.category = category;
        this.instgram = instgram;
        Twitter = twitter;
        this.image = image;
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

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



    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public ProductiveFamily() {

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

    public ProductiveFamily(String name, String details, String latitude, String longitude, String productCategory, String rating, List<String> evaluation, int phone, String category, String image) {
        this.name = name;
        this.details = details;
        this.latitude = latitude;
        this.longitude = longitude;
        this.productCategory = productCategory;
        this.rating = rating;
        this.evaluation = evaluation;
        this.phone = phone;
        this.category = category;
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ProductiveFamily(String name, String details, String latitude, String longitude, String productCategory, String rating, String location, List<String> evaluation, int phone, String category, String image, String id) {
        this.name = name;
        this.details = details;
        this.latitude = latitude;
        this.longitude = longitude;
        this.productCategory = productCategory;
        this.rating = rating;
        this.location = location;
        this.evaluation = evaluation;
        this.phone = phone;
        this.category = category;
        this.image = image;
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProductiveFamily{" +
                "name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", rating='" + rating + '\'' +
                ", location='" + location + '\'' +
                ", evaluation=" + evaluation +
                ", phone=" + phone +
                ", category='" + category + '\'' +
                ", instgram='" + instgram + '\'' +
                ", Twitter='" + Twitter + '\'' +
                ", image='" + image + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public ProductiveFamily(String name, String details, String latitude, String longitude, String productCategory, String rating, List<String> evaluation, int phone, String category, String image, String id) {
        this.name = name;
        this.details = details;
        this.latitude = latitude;
        this.longitude = longitude;
        this.productCategory = productCategory;
        this.rating = rating;
        this.evaluation = evaluation;
        this.phone = phone;
        this.category = category;
        this.image = image;
        this.id = id;
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


    public ProductiveFamily(String name, String details, String category, String image) {
        this.name = name;
        this.details = details;
        this.category = category;
        this.image = image;
    }


}

