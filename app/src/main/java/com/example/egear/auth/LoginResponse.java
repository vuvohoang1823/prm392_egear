package com.example.egear.auth;

public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String role;
    private String accountId;

    public LoginResponse(String accessToken, String refreshToken, String user, String accountId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = user;
        this.accountId = accountId;
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
