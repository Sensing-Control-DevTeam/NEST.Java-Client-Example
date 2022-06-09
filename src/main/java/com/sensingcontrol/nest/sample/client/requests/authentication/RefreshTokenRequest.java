package com.sensingcontrol.nest.sample.client.requests.authentication;

public class RefreshTokenRequest {
    private String userId;
    private String refreshToken;

    public RefreshTokenRequest(String userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }
}
