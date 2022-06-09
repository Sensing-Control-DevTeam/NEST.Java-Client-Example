package com.sensingcontrol.nest.sample.client.model.gateways;

import com.sensingcontrol.nest.sample.client.model.enums.GatewayRegion;

public class Gateway {
    private String id;
    private String eui;
    private GatewayRegion region;
    private String name;
    private String description;
    private boolean online;
    private Long lastUplink;
    private String ownerId;
    private GatewayConfiguration configuration;

    public String getId() {
        return id;
    }
    public String getEui() {
        return eui;
    }
    public GatewayRegion getRegion() { return region; }
    public String getName() { return name; }
    public String getDescription() {
        return description;
    }
    public boolean isOnline() {
        return online;
    }
    public Long getLastUplink() {
        return lastUplink;
    }
    public String getOwnerId() { return ownerId; }
    public GatewayConfiguration getConfiguration() {
        return configuration;
    }
}
