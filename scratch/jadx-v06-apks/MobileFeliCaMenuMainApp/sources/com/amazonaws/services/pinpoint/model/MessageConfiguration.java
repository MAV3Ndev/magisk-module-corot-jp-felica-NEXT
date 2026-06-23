package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class MessageConfiguration implements Serializable {
    private Message aDMMessage;
    private Message aPNSMessage;
    private Message baiduMessage;
    private Message defaultMessage;
    private CampaignEmailMessage emailMessage;
    private Message gCMMessage;
    private CampaignSmsMessage sMSMessage;

    public Message getADMMessage() {
        return this.aDMMessage;
    }

    public void setADMMessage(Message message) {
        this.aDMMessage = message;
    }

    public MessageConfiguration withADMMessage(Message message) {
        this.aDMMessage = message;
        return this;
    }

    public Message getAPNSMessage() {
        return this.aPNSMessage;
    }

    public void setAPNSMessage(Message message) {
        this.aPNSMessage = message;
    }

    public MessageConfiguration withAPNSMessage(Message message) {
        this.aPNSMessage = message;
        return this;
    }

    public Message getBaiduMessage() {
        return this.baiduMessage;
    }

    public void setBaiduMessage(Message message) {
        this.baiduMessage = message;
    }

    public MessageConfiguration withBaiduMessage(Message message) {
        this.baiduMessage = message;
        return this;
    }

    public Message getDefaultMessage() {
        return this.defaultMessage;
    }

    public void setDefaultMessage(Message message) {
        this.defaultMessage = message;
    }

    public MessageConfiguration withDefaultMessage(Message message) {
        this.defaultMessage = message;
        return this;
    }

    public CampaignEmailMessage getEmailMessage() {
        return this.emailMessage;
    }

    public void setEmailMessage(CampaignEmailMessage campaignEmailMessage) {
        this.emailMessage = campaignEmailMessage;
    }

    public MessageConfiguration withEmailMessage(CampaignEmailMessage campaignEmailMessage) {
        this.emailMessage = campaignEmailMessage;
        return this;
    }

    public Message getGCMMessage() {
        return this.gCMMessage;
    }

    public void setGCMMessage(Message message) {
        this.gCMMessage = message;
    }

    public MessageConfiguration withGCMMessage(Message message) {
        this.gCMMessage = message;
        return this;
    }

    public CampaignSmsMessage getSMSMessage() {
        return this.sMSMessage;
    }

    public void setSMSMessage(CampaignSmsMessage campaignSmsMessage) {
        this.sMSMessage = campaignSmsMessage;
    }

    public MessageConfiguration withSMSMessage(CampaignSmsMessage campaignSmsMessage) {
        this.sMSMessage = campaignSmsMessage;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getADMMessage() != null) {
            sb.append("ADMMessage: " + getADMMessage() + ",");
        }
        if (getAPNSMessage() != null) {
            sb.append("APNSMessage: " + getAPNSMessage() + ",");
        }
        if (getBaiduMessage() != null) {
            sb.append("BaiduMessage: " + getBaiduMessage() + ",");
        }
        if (getDefaultMessage() != null) {
            sb.append("DefaultMessage: " + getDefaultMessage() + ",");
        }
        if (getEmailMessage() != null) {
            sb.append("EmailMessage: " + getEmailMessage() + ",");
        }
        if (getGCMMessage() != null) {
            sb.append("GCMMessage: " + getGCMMessage() + ",");
        }
        if (getSMSMessage() != null) {
            sb.append("SMSMessage: " + getSMSMessage());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((getADMMessage() == null ? 0 : getADMMessage().hashCode()) + 31) * 31) + (getAPNSMessage() == null ? 0 : getAPNSMessage().hashCode())) * 31) + (getBaiduMessage() == null ? 0 : getBaiduMessage().hashCode())) * 31) + (getDefaultMessage() == null ? 0 : getDefaultMessage().hashCode())) * 31) + (getEmailMessage() == null ? 0 : getEmailMessage().hashCode())) * 31) + (getGCMMessage() == null ? 0 : getGCMMessage().hashCode())) * 31) + (getSMSMessage() != null ? getSMSMessage().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MessageConfiguration)) {
            return false;
        }
        MessageConfiguration messageConfiguration = (MessageConfiguration) obj;
        if ((messageConfiguration.getADMMessage() == null) ^ (getADMMessage() == null)) {
            return false;
        }
        if (messageConfiguration.getADMMessage() != null && !messageConfiguration.getADMMessage().equals(getADMMessage())) {
            return false;
        }
        if ((messageConfiguration.getAPNSMessage() == null) ^ (getAPNSMessage() == null)) {
            return false;
        }
        if (messageConfiguration.getAPNSMessage() != null && !messageConfiguration.getAPNSMessage().equals(getAPNSMessage())) {
            return false;
        }
        if ((messageConfiguration.getBaiduMessage() == null) ^ (getBaiduMessage() == null)) {
            return false;
        }
        if (messageConfiguration.getBaiduMessage() != null && !messageConfiguration.getBaiduMessage().equals(getBaiduMessage())) {
            return false;
        }
        if ((messageConfiguration.getDefaultMessage() == null) ^ (getDefaultMessage() == null)) {
            return false;
        }
        if (messageConfiguration.getDefaultMessage() != null && !messageConfiguration.getDefaultMessage().equals(getDefaultMessage())) {
            return false;
        }
        if ((messageConfiguration.getEmailMessage() == null) ^ (getEmailMessage() == null)) {
            return false;
        }
        if (messageConfiguration.getEmailMessage() != null && !messageConfiguration.getEmailMessage().equals(getEmailMessage())) {
            return false;
        }
        if ((messageConfiguration.getGCMMessage() == null) ^ (getGCMMessage() == null)) {
            return false;
        }
        if (messageConfiguration.getGCMMessage() != null && !messageConfiguration.getGCMMessage().equals(getGCMMessage())) {
            return false;
        }
        if ((messageConfiguration.getSMSMessage() == null) ^ (getSMSMessage() == null)) {
            return false;
        }
        return messageConfiguration.getSMSMessage() == null || messageConfiguration.getSMSMessage().equals(getSMSMessage());
    }
}
