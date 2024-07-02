package com.example.egear.customer.products;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ProductService {
    @GET("products")
    Call<ProductResponse> getProducts(@Header("Authorization") String token);
    @GET("products?status=ACTIVE")
    Call<ProductResponse> getActiveProducts(@Header("Authorization") String token);
}
