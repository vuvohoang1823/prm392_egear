package com.example.egear.auth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
