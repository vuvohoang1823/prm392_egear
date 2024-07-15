package com.example.egear.tabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.egear.MapActivity;
import com.example.egear.R;
import com.example.egear.auth.Login;
import com.example.egear.customer.profile.Address;
import com.example.egear.customer.profile.ProfileService;
import com.example.egear.customer.profile.UserProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {
    private TextView profileName, profileEmail, profilePhone, profileAddress, profilePassword;
    private ImageView profileAvatar;
    private Button buttonLogout;
    private UserProfile userProfile;

    private static final String TAG = "ProfileFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Khởi tạo các TextView để hiển thị thông tin người dùng
        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.textView4);
        profilePhone = view.findViewById(R.id.textView5);
        profileAddress = view.findViewById(R.id.textView6);
        profilePassword = view.findViewById(R.id.textView7);
        profileAvatar = view.findViewById(R.id.avatar);
        buttonLogout = view.findViewById(R.id.logoutBtn);

        // Kích hoạt chế độ marquee cho TextView hiển thị địa chỉ
        profileAddress.setSelected(true);

        // Gọi phương thức để lấy thông tin người dùng
        getUserProfile();

        // Xử lý sự kiện khi người dùng nhấn nút Logout
        buttonLogout.setOnClickListener(v -> {
            // Xóa token khỏi SharedPreferences
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("accessToken");
            editor.remove("refreshToken");
            editor.remove("role");
            editor.remove("accountId");
            editor.apply();

            Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getActivity(), Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // Xử lý sự kiện khi người dùng nhấn vào profileAddress
        profileAddress.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MapActivity.class);
            if (userProfile != null && userProfile.getAddress_lng_lat() != null) {
                intent.putExtra("latitude", userProfile.getAddress_lng_lat().getLatitude());
                intent.putExtra("longitude", userProfile.getAddress_lng_lat().getLongitude());
            }
            startActivity(intent);
        });


        return view;
    }

    private void getUserProfile() {
        // Lấy token từ SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9999/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Tạo instance của ProfileService
        ProfileService profileService = retrofit.create(ProfileService.class);

        // Gọi API để lấy thông tin người dùng
        Call<UserProfile> call = profileService.getUserInfo("Bearer " + token);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful()) {
                    userProfile = response.body();
                    if (userProfile != null) {
                        // Log thông tin user profile
                        Log.d(TAG, "UserProfile: " + userProfile.toString());
                        if (userProfile.getAddress_lng_lat() != null) {
                            Log.d(TAG, "Latitude: " + userProfile.getAddress_lng_lat().getLatitude());
                            Log.d(TAG, "Longitude: " + userProfile.getAddress_lng_lat().getLongitude());
                        }

                        // Cập nhật các TextView với dữ liệu lấy từ API
                        profileName.setText(userProfile.getName());
                        profileEmail.setText(userProfile.getEmail());
                        profilePhone.setText(userProfile.getPhone());
                        profileAddress.setText(userProfile.getAddress());
                        Glide.with(getActivity()).load(userProfile.getAvatar_url()).into(profileAvatar);

                        // Ẩn mật khẩu bằng ký tự "*"
                        profilePassword.setText(userProfile.getDescription().replaceAll(".", "*"));
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to load user info", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage());
            }
        });
    }
}
