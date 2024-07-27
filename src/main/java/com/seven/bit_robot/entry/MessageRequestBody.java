package com.seven.bit_robot.entry;

public class MessageRequestBody {
  private String group_id;
  private BitMessage bitMessage;

    public MessageRequestBody(String group_id, BitMessage bitMessage) {
        this.group_id = group_id;
        this.bitMessage = bitMessage;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public BitMessage getMessage() {
        return bitMessage;
    }

    public void setMessage(BitMessage bitMessage) {
        this.bitMessage = bitMessage;
    }
}
