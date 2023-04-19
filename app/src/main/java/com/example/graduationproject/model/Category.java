package com.example.graduationproject.model;

import java.util.List;

public class Category {
    List<String> categories_name;
    List<String> categories_image;

    public Category(List<String> categories_name, List<String> categories_image) {
        this.categories_name = categories_name;
        this.categories_image = categories_image;
    }

    public Category() {
    }

    public List<String> getCategories_name() {
        return categories_name;
    }

    public void setCategories_name(List<String> categories_name) {
        this.categories_name = categories_name;
    }

    public List<String> getCategories_image() {
        return categories_image;
    }

    public void setCategories_image(List<String> categories_image) {
        this.categories_image = categories_image;
    }
}
