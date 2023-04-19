package com.example.graduationproject.model;

public class ProductiveFamily {
    String name;
    String details;
    String latlong;
    String location;

    String evaluation;
    int phone;
    String category;
    String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ProductiveFamily(String name, String details, String latlong, String location, String evaluation, int phone, String category, String image) {
        this.name = name;
        this.details = details;
        this.latlong = latlong;
        this.location = location;
        this.evaluation = evaluation;
        this.phone = phone;
        this.category = category;
        this.image = image;
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
                ", evaluation=" + evaluation +
                ", phone=" + phone +
                ", category='" + category + '\'' +
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

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
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

    public ProductiveFamily(String name, String details, String location, String evaluation) {
        this.name = name;
        this.details = details;
        this.location = location;
        this.evaluation = evaluation;
    }

    public ProductiveFamily(String name, String details, String latlong, String location, String evaluation, int phone, String category) {
        this.name = name;
        this.details = details;
        this.latlong = latlong;
        this.location = location;
        this.evaluation = evaluation;
        this.phone = phone;
        this.category = category;
    }
}
