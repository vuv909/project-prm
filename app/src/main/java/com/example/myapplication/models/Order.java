package com.example.myapplication.models;
import  com.example.myapplication.models.Product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order  {

    private String documentId;
    private String userId;
    private String  orderDate;
    private int totalPrice;
    private List<ProOrder> productList;

    public Order() {
        // Default constructor required for Firestore's toObject() method
    }

    public Order(String userId, String  orderDate, int totalPrice, List<ProOrder> productList) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.productList = productList;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String  orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ProOrder> getProductList() {
        return productList;
    }

    public void setProductList(List<ProOrder> productList) {
        this.productList = productList;
    }
    public void addProduct(ProOrder pro) {
        productList.add(pro);
    }
}