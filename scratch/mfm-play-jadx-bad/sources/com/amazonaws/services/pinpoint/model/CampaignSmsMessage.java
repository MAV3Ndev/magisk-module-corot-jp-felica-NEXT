package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CampaignSmsMessage implements Serializable {
    private String body;
    private String messageType;
    private String senderId;

    public String getBody() {
        return this.body;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public CampaignSmsMessage withBody(String str) {
        this.body = str;
        return this;
    }

    public String getMessageType() {
        return this.messageType;
    }

    public void setMessageType(String str) {
        this.messageType = str;
    }

    public CampaignSmsMessage withMessageType(String str) {
        this.messageType = str;
        return this;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType.toString();
    }

    public CampaignSmsMessage withMessageType(MessageType messageType) {
        this.messageType = messageType.toString();
        return this;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public void setSenderId(String str) {
        this.senderId = str;
    }

    public CampaignSmsMessage withSenderId(String str) {
        this.senderId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getBody() != null) {
            sb.append("Body: " + getBody() + ",");
        }
        if (getMessageType() != null) {
            sb.append("MessageType: " + getMessageType() + ",");
        }
        if (getSenderId() != null) {
            sb.append("SenderId: " + getSenderId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((getBody() == null ? 0 : getBody().hashCode()) + 31) * 31) + (getMessageType() == null ? 0 : getMessageType().hashCode())) * 31) + (getSenderId() != null ? getSenderId().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CampaignSmsMessage)) {
            return false;
        }
        CampaignSmsMessage campaignSmsMessage = (CampaignSmsMessage) obj;
        if ((campaignSmsMessage.getBody() == null) ^ (getBody() == null)) {
            return false;
        }
        if (campaignSmsMessage.getBody() != null && !campaignSmsMessage.getBody().equals(getBody())) {
            return false;
        }
        if ((campaignSmsMessage.getMessageType() == null) ^ (getMessageType() == null)) {
            return false;
        }
        if (campaignSmsMessage.getMessageType() != null && !campaignSmsMessage.getMessageType().equals(getMessageType())) {
            return false;
        }
        if ((campaignSmsMessage.getSenderId() == null) ^ (getSenderId() == null)) {
            return false;
        }
        return campaignSmsMessage.getSenderId() == null || campaignSmsMessage.getSenderId().equals(getSenderId());
    }
}
