package com.sensingcontrol.nest.sample.client.signalr.messages;

public class NewGatewayStatusMessage {
    private String gatewayId;
    private Boolean isOnline;
    private Long lastUplinkDate;

    public String getGatewayId() {
        return gatewayId;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public Long getLastUplinkDate() {
        return lastUplinkDate;
    }
}
