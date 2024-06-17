package com.example.egear.customer.products;

import java.util.List;

public class ProductResponse {
    private String message;
    private List<Product> data;

    public String getMessage() {
        return message;
    }

    public List<Product> getData() {
        return data;
    }
}
