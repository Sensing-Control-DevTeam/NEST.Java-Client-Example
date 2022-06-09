package com.sensingcontrol.nest.sample.client.auth;

import com.sensingcontrol.nest.sample.client.NestClientHolder;
import com.sensingcontrol.nest.sample.client.requests.authentication.RefreshTokenRequest;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import java.io.IOException;

public class TokenAuthenticator implements Authenticator {
    private final TokenStore tokenStore;
    private final NestClientHolder clientHolder;

    public TokenAuthenticator(TokenStore tokenStore, NestClientHolder clientHolder) {
        this.tokenStore = tokenStore;
        this.clientHolder = clientHolder;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        var retrofitResponse = clientHolder.getClient().refreshToken(new RefreshTokenRequest(tokenStore.getUserId(), tokenStore.getRefreshToken())).execute();

        if (retrofitResponse.isSuccessful() && retrofitResponse.body() != null) {
            var refreshTokenResponse = retrofitResponse.body().getData();

            if (refreshTokenResponse != null) {
                tokenStore.updateStore(refreshTokenResponse);

                return response.request().newBuilder()
                        .header("x-custom-auth", tokenStore.getAccessToken())
                        .build();
            }
        }

        return null;
    }
}
