package com.example.egear.customer.order;

import java.util.List;

public class Order {
    private String description;
    private String shipping_address;
    private Long customer_id;
    private String payment_method;
    private List<Item> combo_item_list;
    private List<OrderItem> customer_order_item_list;

    public Order() {
    }

    public Order(String description, String shipping_address, Long customer_id, String payment_method, List<Item> combo_item_list, List<OrderItem> customer_order_item_list) {
        this.description = description;
        this.shipping_address = shipping_address;
        this.customer_id = customer_id;
        this.payment_method = payment_method;
        this.combo_item_list = combo_item_list;
        this.customer_order_item_list = customer_order_item_list;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public List<Item> getCombo_item_list() {
        return combo_item_list;
    }

    public void setCombo_item_list(List<Item> combo_item_list) {
        this.combo_item_list = combo_item_list;
    }

    public List<OrderItem> getCustomer_order_item_list() {
        return customer_order_item_list;
    }

    public void setCustomer_order_item_list(List<OrderItem> customer_order_item_list) {
        this.customer_order_item_list = customer_order_item_list;
    }

    @Override
    public String toString() {
        return "Order{" +
                "description='" + description + '\'' +
                ", shipping_address='" + shipping_address + '\'' +
                ", customer_id=" + customer_id +
                ", payment_method='" + payment_method + '\'' +
                ", combo_item_list=" + combo_item_list +
                ", customer_order_item_list=" + customer_order_item_list +
                '}';
    }
}
