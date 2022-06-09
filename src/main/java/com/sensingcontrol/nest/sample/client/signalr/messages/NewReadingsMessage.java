package com.sensingcontrol.nest.sample.client.signalr.messages;

import com.sensingcontrol.nest.sample.client.signalr.types.DataEntry;

import java.util.Map;

public class NewReadingsMessage {
    private String deviceId;
    private Map<String, DataEntry> readings;

    public String getDeviceId() {
        return deviceId;
    }

    public Map<String, DataEntry> getReadings() {
        return readings;
    }
}
