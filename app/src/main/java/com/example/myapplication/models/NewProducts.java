package com.example.myapplication.models;

import java.io.Serializable;

public class NewProducts implements Serializable {
    private String description;
    private String name;
    int price;
    String img;

    public NewProducts() {
    }

    public NewProducts(String description, String name, int price, String img) {
        this.description = description;
        this.name = name;
        this.price = price;
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
