package com.sensingcontrol.nest.sample.client.signalr.types;

import java.util.List;

public class ConfigEntry {
    private Byte id;
    private List<FieldEntry> fieldEntries;

    public Byte getId() {
        return id;
    }

    public List<FieldEntry> getFieldEntries() {
        return fieldEntries;
    }
}
