package com.sensingcontrol.nest.sample.client.requests.signalr;

public class SignalRUnsubscribeAllRequest {
    private String connectionId;

    public SignalRUnsubscribeAllRequest(String connectionId) {
        this.connectionId = connectionId;
    }

    public String getConnectionId() {
        return connectionId;
    }
}
