package com.example.egear.customer.profile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ProfileService {
   @GET("accounts/user-info")
   Call<UserProfile> getUserInfo(@Header("Authorization") String token);
}
