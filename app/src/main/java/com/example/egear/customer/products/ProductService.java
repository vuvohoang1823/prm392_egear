package com.example.egear.customer.products;
import com.example.egear.admin.products.AddProductRequest;
import com.example.egear.admin.products.AddProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductService {
    @GET("products")
    Call<ProductResponse> getProducts(@Header("Authorization") String token);
    @GET("products?status=ACTIVE")
    Call<ProductResponse> getActiveProducts(@Header("Authorization") String token);
    @POST("products")
    Call<AddProductResponse> addProduct(@Header("Authorization") String token, @Body AddProductRequest product);
    @PUT("products/{id}")
    Call<AddProductResponse> updateProduct(@Header("Authorization") String token, @Path("id") Long id, @Body AddProductRequest product);
    @DELETE("products/{id}")
    Call<Void> deleteProduct(@Header("Authorization") String token, @Path("id") Long id);
}
