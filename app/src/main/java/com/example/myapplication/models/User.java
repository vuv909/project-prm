package com.example.myapplication.models;

import java.io.Serializable;

public class User implements Serializable {
    private  int id;
    private  String Email;
    private  String MobileNumber;
    private  String username;

    private String avatar;

    public User() {}

    public User(int id, String email, String mobileNumber, String username, String avatar) {
        this.id = id;
        Email = email;
        MobileNumber = mobileNumber;
        this.username = username;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
