package com.example.myapplication.models;

import java.io.Serializable;

public class CartItem implements Serializable {
   private int user_id;
   private int product_id;
   private  String product_name;
   private String image_product;
   private int price;
   private int quantity;

    public CartItem() {}
    public CartItem(int user_id, int product_id,String product_name, String image_product, int price, int quantity) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.product_name =product_name;
        this.image_product = image_product;
        this.price = price;
        this.quantity = quantity;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getImage_product() {
        return image_product;
    }

    public void setImage_product(String image_product) {
        this.image_product = image_product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
