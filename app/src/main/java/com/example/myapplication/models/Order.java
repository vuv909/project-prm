package com.example.myapplication.models;
import  com.example.myapplication.models.Product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order  {

    private String documentId;
    private String userId;
    private Date orderDate;
    private int totalPrice;
    private List<Product> productList;

    public Order() {
        // Default constructor required for Firestore's toObject() method
    }

    public Order(String documentId,String userId, Date orderDate, int totalPrice, List<Product> productList) {
        this.documentId = documentId;
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}