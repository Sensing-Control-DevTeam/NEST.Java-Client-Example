package com.sensingcontrol.nest.sample.client.model.devices;

import java.util.List;

public class LoraCommand {
    private Short id;
    private String name;
    private List<LoraCommandParam> params;

    public LoraCommand() { }

    public LoraCommand(Short id) { this.id = id; }

    public Short getId() { return id; }
    public String getName() { return name; }
    public List<LoraCommandParam> getParams() { return params; }

    public void setId(Short id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setParams(List<LoraCommandParam> params) { this.params = params; }
}
