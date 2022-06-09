package com.sensingcontrol.nest.sample.client.model.constants;

public final class NestHubCommands {
    // "Read" commands
    public static final short GET_ALARMS = 0x00;
    public static final short GET_ALIVE_SEND_RATE = 0x01;
    public static final short GET_HUB_ID_AND_FIRMWARE = 0x02;
    public static final short GET_ALL_CONFIGURATION = 0x03;
    public static final short GET_GPS_VALUE = 0x04;
    public static final short GET_GPS_THRESHOLDS = 0x05;
    public static final short GET_ENVIRONMENTAL_VALUES = 0x06;
    public static final short GET_ENVIRONMENTAL_THRESHOLDS = 0x07;
    public static final short GET_MOX_ID_AND_FIRMWARE = 0x08;
    public static final short GET_MOX_CALIBRATION = 0x09;
    public static final short GET_MOX_CONFIGURATION = 0x0A;
    public static final short GET_MOX_VALUES = 0x0B;
    public static final short GET_MOX_THRESHOLDS = 0x0C;
    public static final short GET_RAMAN_ID_AND_FIRMWARE = 0x18;
    public static final short GET_RAMAN_CALIBRATION = 0x19;
    public static final short GET_RAMAN_CONFIGURATION = 0x1A;
    public static final short GET_RAMAN_VALUES = 0x1B;
    public static final short GET_RAMAN_THRESHOLDS = 0x1C;
    public static final short GET_BIOLOGICAL_ID_AND_FIRMWARE = 0x20;
    public static final short GET_BIOLOGICAL_DEBUG_PARAMS = 0x21;
    public static final short GET_BIOLOGICAL_CONFIGURATION = 0x22;
    public static final short GET_BIOLOGICAL_VALUES = 0x23;
    public static final short GET_RADIOLOGICAL_ID_AND_FIRMWARE = 0x30;
    public static final short GET_RADIOLOGICAL_SENSOR_COMMUNICATION = 0x31;
    public static final short GET_RADIOLOGICAL_INTEGRATION_TIME = 0x32;
    public static final short GET_RADIOLOGICAL_TEMPERATURE = 0x33;
    public static final short GET_RADIOLOGICAL_THRESHOLDS = 0x34;
    public static final short GET_RADIOLOGICAL_VALUES = 0x35;

    // "Write" commands
    public static final short HUB_RESET = 0x80;
    public static final short SET_HUB_ALIVE_SEND_RATE = 0x81;
    public static final short SET_GPS_THRESHOLDS = 0x84;
    public static final short SET_ENVIRONMENTAL_THRESHOLDS = 0x85;
    public static final short SET_MOX_CALIBRATION = 0x89;
    public static final short SET_MOX_CONFIGURATION = 0x8A;
    public static final short SET_MOX_THRESHOLDS = 0x8C;
    public static final short SET_RAMAN_CALIBRATION = 0x99;
    public static final short SET_RAMAN_CONFIGURATION = 0x9A;
    public static final short SET_RAMAN_THRESHOLDS = 0x9C;
    public static final short SET_BIOLOGICAL_CONFIGURATION = 0xA2;
    public static final short SET_RADIOLOGICAL_INTEGRATION_TIME = 0xB2;
    public static final short SET_RADIOLOGICAL_THRESHOLDS = 0xB4;
}
