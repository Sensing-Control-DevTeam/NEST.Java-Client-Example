package com.sensingcontrol.nest.sample.client.auth;

import okhttp3.Interceptor;
import okhttp3.Response;
import java.io.IOException;

public class TokenInterceptor implements Interceptor {
    private final TokenStore tokenStore;

    public TokenInterceptor(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // Add default headers
        var requestBuilder = chain
                .request()
                .newBuilder()
                .addHeader("accept", "*/*");

        if (tokenStore.getAccessToken() != null) {
            requestBuilder.addHeader("x-custom-auth", tokenStore.getAccessToken());
        }

        return chain.proceed(requestBuilder.build());
    }
}
