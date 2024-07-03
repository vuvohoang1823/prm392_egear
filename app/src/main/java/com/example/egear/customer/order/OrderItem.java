package com.example.egear.customer.order;

public class OrderItem {
    private int quantity;
    private Long product_id;

    public OrderItem(int quantity, Long product_id) {
        this.quantity = quantity;
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "quantity=" + quantity +
                ", product_id=" + product_id +
                '}';
    }
}
