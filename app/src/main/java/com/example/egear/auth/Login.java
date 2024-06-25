package com.example.egear.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.egear.AdminActivity;
import com.example.egear.MainActivity;
import com.example.egear.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        Button loginButton = (Button) findViewById(R.id.btnLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameValue = username.getText().toString();
                String passwordValue = password.getText().toString();

                login(username, password);
            }
        });
    }

    private boolean checkInput(String username, String password) {
        // Check if username and password are empty
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Username or Password is empty", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return false;
        }
    }

    private void login(EditText username, EditText password) {
        String usernameValue = username.getText().toString();
        String passwordValue = password.getText().toString();

        if (usernameValue.isEmpty() || passwordValue.isEmpty()) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:9999/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthService authService = retrofit.create(AuthService.class);
        Call<LoginResponse> call = authService.login(new LoginRequest(usernameValue, passwordValue));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    // Save the token and user ID (e.g., in SharedPreferences)
                    saveLoginInfo(loginResponse);
                    // Navigate based on user role
                    if (loginResponse.getRole().equals("ADMIN")) {
                        Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, AdminActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (loginResponse.getRole().equals("CUSTOMER")) {
                        Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    // Handle error response
                    Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Handle network or unexpected errors
                Toast.makeText(Login.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveLoginInfo(LoginResponse loginResponse) {
        SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("accessToken", loginResponse.getAccessToken());
        editor.putString("refreshToken", loginResponse.getRefreshToken());
        editor.putString("role", loginResponse.getRole());
        editor.putString("accountId", loginResponse.getAccountId());
        editor.apply();
    }
}
