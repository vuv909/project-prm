package com.example.myapplication.models;

public class ProOrder {
    private String orderDate;
    private int totalPrice;
    private String userId;
    private int totalProduct;

    public ProOrder() {}

    public ProOrder(String orderDate, int totalPrice, String userId, int totalProduct) {
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.totalProduct = totalProduct;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }
}
