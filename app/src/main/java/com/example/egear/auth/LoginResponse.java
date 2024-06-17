package com.example.egear.auth;

public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String role;

    public LoginResponse(String accessToken, String refreshToken, String user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
