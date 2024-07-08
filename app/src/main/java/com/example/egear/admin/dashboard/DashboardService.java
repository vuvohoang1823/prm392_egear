package com.example.egear.admin.dashboard;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface DashboardService {
    @GET("dashboard")
    Call<OrderResponse> getDashboardData(@Header("Authorization") String token);
}
