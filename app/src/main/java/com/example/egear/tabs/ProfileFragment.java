package com.example.egear.tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.egear.R;
import com.example.egear.customer.profile.ProfileService;
import com.example.egear.customer.profile.UserProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {
    private TextView profileName, profileEmail, profilePhone, profileAddress, profilePassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.textView4);
        profilePhone = view.findViewById(R.id.textView5);
        profileAddress = view.findViewById(R.id.textView6);
        profilePassword = view.findViewById(R.id.textView7);

        getUserProfile();

        return view;
    }

    private void getUserProfile() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9999/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProfileService profileService = retrofit.create(ProfileService.class);
        Call<UserProfile> call = profileService.getUserInfo("Bearer " + token);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful()) {
                    UserProfile userProfile = response.body();
                    if (userProfile != null) {
                        profileName.setText(userProfile.getName());
                        profileEmail.setText(userProfile.getEmail());
                        profilePhone.setText(userProfile.getPhone());
                        profileAddress.setText(userProfile.getAddress());
                        profilePassword.setText(userProfile.getDescription());
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to load user info", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
