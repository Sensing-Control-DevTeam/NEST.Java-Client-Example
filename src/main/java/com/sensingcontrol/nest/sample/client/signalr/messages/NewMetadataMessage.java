package com.sensingcontrol.nest.sample.client.signalr.messages;

import java.util.Map;

public class NewMetadataMessage {
    private String deviceId;
    private Map<String, String> deviceMetadata;
    private Map<String, Map<String, String>> sensorsMetadata;

    public String getDeviceId() {
        return deviceId;
    }

    public Map<String, String> getDeviceMetadata() {
        return deviceMetadata;
    }

    public Map<String, Map<String, String>> getSensorsMetadata() {
        return sensorsMetadata;
    }
}
