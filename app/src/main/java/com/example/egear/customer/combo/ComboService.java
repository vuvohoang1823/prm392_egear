package com.example.egear.customer.combo;

import com.example.egear.customer.products.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ComboService {
    @GET("combos")
    Call<ComboResponse> getCombos(@Header("Authorization") String token);
    @GET("combos/{id}")
    Call<ComboDetailResponse> getProductsInCombo(@Header("Authorization") String token, @Path("id") Long id);
}
