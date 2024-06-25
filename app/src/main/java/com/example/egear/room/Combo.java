package com.example.egear.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "combo")
public class Combo {
    @PrimaryKey
    @NonNull
    private Long id;
    private String name;
    private String description;
    private Long products_total;
    private String img_url;
    private String discount_by_percent;
    private Long discount_by_value;
    private int quantity;

    public Combo(@NonNull Long id, String name, String description, Long products_total, String img_url, String discount_by_percent, Long discount_by_value, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products_total = products_total;
        this.img_url = img_url;
        this.discount_by_percent = discount_by_percent;
        this.discount_by_value = discount_by_value;
        this.quantity = quantity;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
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

    public Long getProducts_total() {
        return products_total;
    }

    public void setProducts_total(Long products_total) {
        this.products_total = products_total;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDiscount_by_percent() {
        return discount_by_percent;
    }

    public void setDiscount_by_percent(String discount_by_percent) {
        this.discount_by_percent = discount_by_percent;
    }

    public Long getDiscount_by_value() {
        return discount_by_value;
    }

    public void setDiscount_by_value(Long discount_by_value) {
        this.discount_by_value = discount_by_value;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
