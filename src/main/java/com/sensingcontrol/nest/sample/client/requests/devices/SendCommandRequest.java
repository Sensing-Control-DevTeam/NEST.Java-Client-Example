package com.sensingcontrol.nest.sample.client.requests.devices;

import com.sensingcontrol.nest.sample.client.model.devices.LoraCommand;

public class SendCommandRequest {
    private LoraCommand command;

    public SendCommandRequest(LoraCommand command) {
        this.command = command;
    }
}
