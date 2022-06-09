package com.sensingcontrol.nest.sample.client.signalr.messages;

import com.sensingcontrol.nest.sample.client.signalr.types.ConfigEntry;

import java.util.Map;

public class NewConfigurationMessage {
    private String deviceId;
    private Map<String, ConfigEntry> deviceConfiguration;
    private Map<String, ConfigEntry> sensorsConfiguration;

    public String getDeviceId() {
        return deviceId;
    }

    public Map<String, ConfigEntry> getDeviceConfiguration() { return deviceConfiguration; }
    public Map<String, ConfigEntry> getSensorsConfiguration() { return sensorsConfiguration; }
}
