package com.example.egear.admin.dashboard;

import com.example.egear.customer.profile.UserProfile;

import java.util.Date;
import java.util.List;

public class OrderResponse {
    private String message;
    private Dashboard data;

    public OrderResponse() {
    }

    public OrderResponse(String message, Dashboard data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Dashboard getData() {
        return data;
    }

    public void setData(Dashboard data) {
        this.data = data;
    }
}
