package com.example.myapplication.models;

public class ProOrder {
    private String productId;
    private String productName;
    private String imageProduct;
    private int price;
    private int quantity;

    public ProOrder() {}

    public ProOrder(String productId, String productName, String imageProduct, int price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.imageProduct = imageProduct;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
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
