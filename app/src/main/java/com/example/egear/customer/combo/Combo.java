package com.example.egear.customer.combo;

import java.io.Serializable;

public class Combo implements Serializable{
    private Long id;
    private String name;
    private String description;
    private Long price;
    private String image_url;
    private String discount_by_percent;
    private Long discount_by_value;

    public Combo(Long id, String name, String description, Long price, String image_url, String discount_by_percent, Long discount_by_value) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
        this.discount_by_percent = discount_by_percent;
        this.discount_by_value = discount_by_value;
    }

    public Combo(String name, String description, Long price, String discount_by_percent, Long discount_by_value) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount_by_percent = discount_by_percent;
        this.discount_by_value = discount_by_value;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImageUrl() {
        return image_url;
    }

    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }

    public String getPercentDiscount() {
        return discount_by_percent;
    }

    public void setPercentDiscount(String discount_by_percent) {
        this.discount_by_percent = discount_by_percent;
    }

    public Long getValueDiscount() {
        return discount_by_value;
    }

    public void setValueDiscount(Long discount_by_value) {
        this.discount_by_value = discount_by_value;
    }
}
