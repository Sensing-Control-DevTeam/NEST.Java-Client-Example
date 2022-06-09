package com.sensingcontrol.nest.sample.client.model.devices;

import java.util.List;
import java.util.Map;

public class Sensor {
    private String id;
    private String name;
    private Boolean isOnline;
    private Map<String, String> metadata;
    private List<SensorReading> readings;
    private List<LoraCommand> configuration;

    public String getId() {
        return id;
    }
    public String getName() { return name; }
    public Boolean isOnline() {
        return isOnline;
    }
    public Map<String, String> getMetadata() { return metadata; }
    public List<SensorReading> getReadings() { return readings; }
    public List<LoraCommand> getConfiguration() { return configuration; }
}
