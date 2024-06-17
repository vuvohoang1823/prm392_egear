package com.example.egear.customer.combo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ComboService {
    @GET("/combos")
    Call<List<Combo>> getCombos();
}
