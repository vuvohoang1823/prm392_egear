package com.example.egear.admin.dashboard;

import com.example.egear.customer.profile.UserProfile;

import java.util.List;

public class Dashboard {
    private Long total_income;
    private int product_sold_count;
    private UserProfile top_customer;
    private List<OrderHistory> customer_orders;

    public Dashboard() {
    }

    public Dashboard(Long total_income, int product_sold_count, UserProfile top_customer, List<OrderHistory> customer_orders) {
        this.total_income = total_income;
        this.product_sold_count = product_sold_count;
        this.top_customer = top_customer;
        this.customer_orders = customer_orders;
    }

    public Long getTotal_income() {
        return total_income;
    }

    public void setTotal_income(Long total_income) {
        this.total_income = total_income;
    }

    public int getProduct_sold_count() {
        return product_sold_count;
    }

    public void setProduct_sold_count(int product_sold_count) {
        this.product_sold_count = product_sold_count;
    }

    public UserProfile getTop_customer() {
        return top_customer;
    }

    public void setTop_customer(UserProfile top_customer) {
        this.top_customer = top_customer;
    }

    public List<OrderHistory> getCustomer_orders() {
        return customer_orders;
    }

    public void setCustomer_orders(List<OrderHistory> customer_orders) {
        this.customer_orders = customer_orders;
    }

    @Override
    public String toString() {
        return "Dashboard{" +
                "total_income=" + total_income +
                ", product_sold_count=" + product_sold_count +
                ", top_customer=" + top_customer +
                ", customer_orders=" + customer_orders +
                '}';
    }
}
