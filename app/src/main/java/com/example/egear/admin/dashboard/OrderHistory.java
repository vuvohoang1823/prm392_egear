package com.example.egear.admin.dashboard;

import java.util.Date;

public class OrderHistory {
    private Long id;
    private String name;
    private String description;
    private String status;
    private String payment_method;
    private String net_amount;
    private Date order_date;

    public OrderHistory() {
    }

    public OrderHistory(Long id, String name, String description, String status, String payment_method, String net_amount, Date order_date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.payment_method = payment_method;
        this.net_amount = net_amount;
        this.order_date = order_date;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getNet_amount() {
        return net_amount;
    }

    public void setNet_amount(String net_amount) {
        this.net_amount = net_amount;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    @Override
    public String toString() {
        return "OrderHistory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", payment_method='" + payment_method + '\'' +
                ", net_amount='" + net_amount + '\'' +
                ", order_date=" + order_date +
                '}';
    }
}
