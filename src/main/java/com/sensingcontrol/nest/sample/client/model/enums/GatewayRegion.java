package com.sensingcontrol.nest.sample.client.model.enums;

import com.google.gson.annotations.SerializedName;

public enum GatewayRegion {
    @SerializedName("AU915")
    AU915,
    @SerializedName("EU868")
    EU868,
    @SerializedName("US915")
    US915,
    @SerializedName("AS923-1")
    AS923_1
}
