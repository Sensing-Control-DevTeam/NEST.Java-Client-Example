package com.sensingcontrol.nest.sample.client.model.devices;

import com.sensingcontrol.nest.sample.client.model.enums.DataType;

public class HistoricDataEntry {
    private String id;
    private String partitionKey;
    private String readingId;
    private DataType dataType;
    private String units;
    private Long timestamp;
    private Object value;

    public String getId() { return id; }
    public String getPartitionKey() { return partitionKey; }
    public String getReadingId() { return readingId; }
    public DataType getDataType() { return dataType; }
    public String getUnits() { return units; }
    public Long getTimestamp() { return timestamp; }
    public Object getValue() { return value; }
}
