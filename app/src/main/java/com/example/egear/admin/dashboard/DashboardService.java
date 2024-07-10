package com.example.egear.admin.dashboard;

import androidx.annotation.Nullable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DashboardService {
    @GET("dashboard")
    Call<OrderResponse> getDashboardData(@Header("Authorization") String token);
    @GET("dashboard")
    Call<OrderResponse> getFilteredDashboardData(@Header("Authorization") String token, @Nullable @Query("month") String month);
}
