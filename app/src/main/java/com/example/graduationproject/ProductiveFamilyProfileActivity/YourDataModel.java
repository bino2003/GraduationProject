package com.example.graduationproject.ProductiveFamilyProfileActivity;

public class YourDataModel {
    private String imageUrl;

    public YourDataModel() {
        // Empty constructor required for Firestore
    }

    public YourDataModel(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
