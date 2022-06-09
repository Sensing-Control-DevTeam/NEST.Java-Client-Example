package com.sensingcontrol.nest.sample.client.model.devices;

import java.util.List;
import java.util.Map;

public class Device {
    private String id;
    private String ownerId;
    private String name;
    private String description;
    private String type;
    private String lastUplinkData;
    private Long lastUplinkDate;
    private Map<String, String> metadata;
    private List<LoraCommand> commands;
    private List<LoraCommand> configuration;
    private List<Sensor> sensors;

    public String getId() {
        return id;
    }
    public String getOwnerId() { return ownerId; }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getType() { return type; }
    public String getLastUplinkData() { return lastUplinkData; }
    public Long getLastUplinkDate() { return lastUplinkDate; }
    public Map<String, String> getMetadata() { return metadata; }
    public List<LoraCommand> getCommands() { return commands; }
    public List<LoraCommand> getConfiguration() { return configuration; }
    public List<Sensor> getSensors() {
        return sensors;
    }
}
