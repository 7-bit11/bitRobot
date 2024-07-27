package com.seven.bit_robot.entry;

import com.fasterxml.jackson.annotation.*;

public class Sender {
    private long userID;
    private String nickname;
    private String card;
    private String role;

    @JsonProperty("user_id")
    public long getUserID() { return userID; }
    @JsonProperty("user_id")
    public void setUserID(long value) { this.userID = value; }

    @JsonProperty("nickname")
    public String getNickname() { return nickname; }
    @JsonProperty("nickname")
    public void setNickname(String value) { this.nickname = value; }

    @JsonProperty("card")
    public String getCard() { return card; }
    @JsonProperty("card")
    public void setCard(String value) { this.card = value; }

    @JsonProperty("role")
    public String getRole() { return role; }
    @JsonProperty("role")
    public void setRole(String value) { this.role = value; }
}
