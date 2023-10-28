package com.example.myapplication.models;

import java.io.Serializable;

public class Category implements Serializable {
    private String img ;
    private String name ;
    private String type;

    public Category() {
    }

    public Category(String img, String name, String type) {
        this.img = img;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
