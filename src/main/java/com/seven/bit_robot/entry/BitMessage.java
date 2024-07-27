package com.seven.bit_robot.entry;

import com.fasterxml.jackson.annotation.*;

public class BitMessage {
    private Data data;
    private String type;

    @JsonProperty("data")
    public Data getData() { return data; }
    @JsonProperty("data")
    public void setData(Data value) { this.data = value; }

    @JsonProperty("type")
    public String getType() { return type; }
    @JsonProperty("type")
    public void setType(String value) { this.type = value; }
}
