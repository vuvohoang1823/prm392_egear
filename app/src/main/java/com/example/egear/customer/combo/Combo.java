package com.example.egear.customer.combo;

import com.example.egear.customer.products.Product;

import java.io.Serializable;
import java.util.List;

public class Combo implements Serializable{
    private Long id;
    private String name;
    private String description;
    private Long products_total;
    private String img_url;
    private String discount_by_percent;
    private String discount_by_value;
    private List<Product> products;

    public Combo() {
    }

    public Combo(Long id, String name, String description, Long products_total, String img_url, String discount_by_percent, String discount_by_value) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products_total = products_total;
        this.img_url = img_url;
        this.discount_by_percent = discount_by_percent;
        this.discount_by_value = discount_by_value;
    }

    public Combo(String name, String description, String discount_by_percent, String discount_by_value, List<Product> products) {
        this.name = name;
        this.description = description;
        this.discount_by_percent = discount_by_percent;
        this.discount_by_value = discount_by_value;
        this.products = products;
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
        return products_total;
    }

    public void setPrice(Long products_total) {
        this.products_total = products_total;
    }

    public String getImageUrl() {
        return img_url;
    }

    public void setImageUrl(String img_url) {
        this.img_url = img_url;
    }

    public String getPercentDiscount() {
        return discount_by_percent;
    }

    public void setPercentDiscount(String discount_by_percent) {
        this.discount_by_percent = discount_by_percent;
    }

    public String getValueDiscount() {
        return discount_by_value;
    }

    public void setValueDiscount(String discount_by_value) {
        this.discount_by_value = discount_by_value;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Combo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", products_total=" + products_total +
                ", img_url='" + img_url + '\'' +
                ", discount_by_percent='" + discount_by_percent + '\'' +
                ", discount_by_value=" + discount_by_value +
                ", products=" + products +
                '}';
    }
}
