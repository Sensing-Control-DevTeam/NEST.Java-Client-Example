package com.sensingcontrol.nest.sample.client.signalr.messages;

public class NewUplinkMessage {
    private String deviceId;
    private String payload;
    private Long timestamp;

    public String getDeviceId() {
        return deviceId;
    }

    public String getPayload() {
        return payload;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
