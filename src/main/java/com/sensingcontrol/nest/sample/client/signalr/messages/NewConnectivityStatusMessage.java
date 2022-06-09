package com.sensingcontrol.nest.sample.client.signalr.messages;

import java.util.Map;

public class NewConnectivityStatusMessage {
    private String deviceId;
    private Map<String, Boolean> connectivityStatus;

    public String getDeviceId() {
        return deviceId;
    }

    public Map<String, Boolean> getConnectivityStatus() {
        return connectivityStatus;
    }
}
