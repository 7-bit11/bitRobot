package com.seven.bit_robot.entry;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class MessageEntry {
    private long selfID;
    private long userID;
    private long time;
    private long messageID;
    private long realID;
    private long messageSeq;
    private String messageType;
    private Sender sender;
    private String rawMessage;
    private long font;
    private String subType;
    private List<Message> message;
    private String messageFormat;
    private String postType;
    private long groupID;

    @JsonProperty("self_id")
    public long getSelfID() { return selfID; }
    @JsonProperty("self_id")
    public void setSelfID(long value) { this.selfID = value; }

    @JsonProperty("user_id")
    public long getUserID() { return userID; }
    @JsonProperty("user_id")
    public void setUserID(long value) { this.userID = value; }

    @JsonProperty("time")
    public long getTime() { return time; }
    @JsonProperty("time")
    public void setTime(long value) { this.time = value; }

    @JsonProperty("message_id")
    public long getMessageID() { return messageID; }
    @JsonProperty("message_id")
    public void setMessageID(long value) { this.messageID = value; }

    @JsonProperty("real_id")
    public long getRealID() { return realID; }
    @JsonProperty("real_id")
    public void setRealID(long value) { this.realID = value; }

    @JsonProperty("message_seq")
    public long getMessageSeq() { return messageSeq; }
    @JsonProperty("message_seq")
    public void setMessageSeq(long value) { this.messageSeq = value; }

    @JsonProperty("message_type")
    public String getMessageType() { return messageType; }
    @JsonProperty("message_type")
    public void setMessageType(String value) { this.messageType = value; }

    @JsonProperty("sender")
    public Sender getSender() { return sender; }
    @JsonProperty("sender")
    public void setSender(Sender value) { this.sender = value; }

    @JsonProperty("raw_message")
    public String getRawMessage() { return rawMessage; }
    @JsonProperty("raw_message")
    public void setRawMessage(String value) { this.rawMessage = value; }

    @JsonProperty("font")
    public long getFont() { return font; }
    @JsonProperty("font")
    public void setFont(long value) { this.font = value; }

    @JsonProperty("sub_type")
    public String getSubType() { return subType; }
    @JsonProperty("sub_type")
    public void setSubType(String value) { this.subType = value; }

    @JsonProperty("message")
    public List<Message> getMessage() { return message; }
    @JsonProperty("message")
    public void setMessage(List<Message> value) { this.message = value; }

    @JsonProperty("message_format")
    public String getMessageFormat() { return messageFormat; }
    @JsonProperty("message_format")
    public void setMessageFormat(String value) { this.messageFormat = value; }

    @JsonProperty("post_type")
    public String getPostType() { return postType; }
    @JsonProperty("post_type")
    public void setPostType(String value) { this.postType = value; }

    @JsonProperty("group_id")
    public long getGroupID() { return groupID; }
    @JsonProperty("group_id")
    public void setGroupID(long value) { this.groupID = value; }
}
