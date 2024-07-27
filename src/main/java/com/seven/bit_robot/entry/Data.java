package com.seven.bit_robot.entry;

import com.fasterxml.jackson.annotation.*;

public class Data {
    private String text;

    @JsonProperty("text")
    public String getText() { return text; }
    @JsonProperty("text")
    public void setText(String value) { this.text = value; }
}
