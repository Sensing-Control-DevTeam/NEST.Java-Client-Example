package com.sensingcontrol.nest.sample.client.requests.signalr;

public class SignalRSubscribeRequest {
    private String connectionId;
    private String groupName;

    public SignalRSubscribeRequest(String connectionId, String groupName) {
        this.connectionId = connectionId;
        this.groupName = groupName;
    }

    public String getConnectionId() {
        return connectionId;
    }

    public String getGroupName() {
        return groupName;
    }
}
