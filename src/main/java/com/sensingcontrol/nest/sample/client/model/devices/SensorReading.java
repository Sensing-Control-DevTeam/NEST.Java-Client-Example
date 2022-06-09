package com.sensingcontrol.nest.sample.client.model.devices;

import com.sensingcontrol.nest.sample.client.model.enums.DataType;

public class SensorReading {
    private String id;
    private String name;
    private DataType type;
    private String units;
    private Object value;
    private Long timestamp;
    private Boolean isDebug;

    public String getId() { return id; }
    public String getName() { return name; }
    public DataType getType() { return type; }
    public String getUnits() { return units; }
    public Object getValue() { return value; }
    public Long getTimestamp() { return timestamp; }
    public Boolean isDebug() { return isDebug; }
}
