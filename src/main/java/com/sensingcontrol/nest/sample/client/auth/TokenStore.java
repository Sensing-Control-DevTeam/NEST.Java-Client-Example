package com.sensingcontrol.nest.sample.client.auth;

import com.google.gson.Gson;
import com.sensingcontrol.nest.sample.client.responses.TokensData;

import java.util.Base64;

public class TokenStore {
    private String userId;
    private String accessToken;
    private long expiration;
    private String refreshToken;

    public String getUserId() { return userId; }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateStore(TokensData data) {
        this.accessToken = data.getAccessToken();
        this.expiration = data.getAccessTokenExpirationDate();
        this.refreshToken = data.getRefreshToken();
        this.userId = extractUserId(data.getAccessToken());
    }

    private String extractUserId(String jwtToken) {
        String[] split_string = jwtToken.split("\\.");

        return new Gson().fromJson(new String(Base64.getUrlDecoder().decode(split_string[1])), JwtTokenData.class).getSid();
    }

    private static class JwtTokenData {
        private String sid;

        public String getSid() {
            return sid;
        }
    }
}
