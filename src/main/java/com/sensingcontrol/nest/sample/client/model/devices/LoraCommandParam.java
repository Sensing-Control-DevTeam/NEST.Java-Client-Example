package com.sensingcontrol.nest.sample.client.model.devices;

public class LoraCommandParam {
    private String id;
    private String name;
    private Long minValue;
    private Long maxValue;
    private Integer size;
    private Long value;

    public LoraCommandParam() { }

    public LoraCommandParam(String id, Long value) {
        this.id = id;
        this.value = value;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Long getMinValue() { return minValue; }
    public Long getMaxValue() { return maxValue; }
    public Integer getSize() { return size; }
    public Long getValue() { return value; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setMinValue(Long minValue) { this.minValue = minValue; }
    public void setMaxValue(Long maxValue) { this.maxValue = maxValue; }
    public void setSize(Integer size) { this.size = size; }
    public void setValue(Long value) { this.value = value; }
}
