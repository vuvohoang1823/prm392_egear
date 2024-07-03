package com.example.egear.admin.combo;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ImageService {
    @Multipart
    @POST("media-files/file")
    Call<ImageResponse> uploadImage(@Header("Authorization") String token, @Part List<MultipartBody.Part> files);
}
