package com.example.egear.customer.products;

import com.example.egear.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {
    @GET("/products")
    Call<List<Post>> getProducts();
}
