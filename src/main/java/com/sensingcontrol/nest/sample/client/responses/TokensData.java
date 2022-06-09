package com.sensingcontrol.nest.sample.client.responses;

public class TokensData {
    private String accessToken;
    private long accessTokenExpirationDate;
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public long getAccessTokenExpirationDate() {
        return accessTokenExpirationDate;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
