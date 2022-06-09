package com.sensingcontrol.nest.sample.client.requests.signalr;

public class SignalRSubscribeAllRequest {
    private String connectionId;

    public SignalRSubscribeAllRequest(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getConnectionId() {
        return connectionId;
    }
}
