package com.example.egear;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostService {
    @GET("/posts")
    Call<ArrayList<Post>> getPosts();

}
