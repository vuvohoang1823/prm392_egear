package com.example.egear.customer.order;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OrderService {
    @POST("customer-orders")
    Call<OrderResponse> createOrder(@Header("Authorization") String token, @Body Order order);
}
