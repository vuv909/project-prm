package com.example.myapplication.models;

import java.io.Serializable;
import java.util.Date;


public class Order implements Serializable {
    private int id;
    private int product_id;
    private int user_id;
    private int total_price;
    private int product_quantity;
    private String product_name;
    private String product_image;
    private String status;
    private String address;
    private Date order_date;

    public Order() {
    }

    public Order(int id, int product_id, int user_id, int total_price, int product_quantity, String product_name, String product_image, String status, String address, Date order_date) {
        this.id = id;
        this.product_id = product_id;
        this.user_id = user_id;
        this.total_price = total_price;
        this.product_quantity = product_quantity;
        this.product_name = product_name;
        this.product_image = product_image;
        this.status = status;
        this.address = address;
        this.order_date = order_date;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }
}
