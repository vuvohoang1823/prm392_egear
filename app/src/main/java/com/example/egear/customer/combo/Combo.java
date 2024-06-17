package com.example.egear.customer.combo;

import java.io.Serializable;

public class Combo implements Serializable{
    private Long id;
    private String name;
    private String description;
    private String price;
    private String image;
    private int percentDiscount;

    public Combo(Long id, String name, String description, String price, String image, int percentDiscount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.percentDiscount = percentDiscount;
    }

    public Combo(String name, String description, String price, int percentDiscount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.percentDiscount = percentDiscount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(int percentDiscount) {
        this.percentDiscount = percentDiscount;
    }
}
