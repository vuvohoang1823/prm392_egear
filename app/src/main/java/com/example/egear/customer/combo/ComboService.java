package com.example.egear.customer.combo;

import com.example.egear.admin.combo.AddComboRequest;
import com.example.egear.admin.combo.AddComboResponse;
import com.example.egear.customer.products.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ComboService {
    @GET("combos")
    Call<ComboResponse> getCombos(@Header("Authorization") String token);
    @GET("combos?status=ACTIVE")
    Call<ComboResponse> getActiveCombos(@Header("Authorization") String token);
    @GET("combos/{id}")
    Call<ComboDetailResponse> getProductsInCombo(@Header("Authorization") String token, @Path("id") Long id);
    @POST("combos")
    Call<AddComboResponse> createCombo(@Header("Authorization") String token, @Body AddComboRequest combo);
    @PUT("combos/{id}")
    Call<AddComboResponse> updateCombo(@Header("Authorization") String token, @Path("id") Long id, @Body AddComboRequest combo);
    @DELETE("combos/{id}")
    Call<Void> deleteCombo(@Header("Authorization") String token, @Path("id") Long id);
}
