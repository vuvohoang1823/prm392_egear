package com.example.egear.customer.combo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ComboService {
    @GET("combos")
    Call<ComboResponse> getCombos(@Header("Authorization") String token);
}
