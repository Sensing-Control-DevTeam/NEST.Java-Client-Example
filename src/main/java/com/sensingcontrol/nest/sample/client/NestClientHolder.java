package com.sensingcontrol.nest.sample.client;

public class NestClientHolder {
    private INestApiClient client;

    public INestApiClient getClient() {
        return client;
    }

    public void setClient(INestApiClient client) {
        this.client = client;
    }
}
