package com.example.myapplication.models;

import java.io.Serializable;

public class Category implements Serializable {
    private int id ;
    private String img ;
    private String name;

    public Category() {
    }

    public Category(int id, String img, String name) {
        this.id = id;
        this.img = img;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
