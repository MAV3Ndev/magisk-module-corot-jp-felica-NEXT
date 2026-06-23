package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DirectMessageConfiguration implements Serializable {
    private ADMMessage aDMMessage;
    private APNSMessage aPNSMessage;
    private BaiduMessage baiduMessage;
    private DefaultMessage defaultMessage;
    private DefaultPushNotificationMessage defaultPushNotificationMessage;
    private EmailMessage emailMessage;
    private GCMMessage gCMMessage;
    private SMSMessage sMSMessage;
    private VoiceMessage voiceMessage;

    public ADMMessage getADMMessage() {
        return this.aDMMessage;
    }

    public void setADMMessage(ADMMessage aDMMessage) {
        this.aDMMessage = aDMMessage;
    }

    public DirectMessageConfiguration withADMMessage(ADMMessage aDMMessage) {
        this.aDMMessage = aDMMessage;
        return this;
    }

    public APNSMessage getAPNSMessage() {
        return this.aPNSMessage;
    }

    public void setAPNSMessage(APNSMessage aPNSMessage) {
        this.aPNSMessage = aPNSMessage;
    }

    public DirectMessageConfiguration withAPNSMessage(APNSMessage aPNSMessage) {
        this.aPNSMessage = aPNSMessage;
        return this;
    }

    public BaiduMessage getBaiduMessage() {
        return this.baiduMessage;
    }

    public void setBaiduMessage(BaiduMessage baiduMessage) {
        this.baiduMessage = baiduMessage;
    }

    public DirectMessageConfiguration withBaiduMessage(BaiduMessage baiduMessage) {
        this.baiduMessage = baiduMessage;
        return this;
    }

    public DefaultMessage getDefaultMessage() {
        return this.defaultMessage;
    }

    public void setDefaultMessage(DefaultMessage defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public DirectMessageConfiguration withDefaultMessage(DefaultMessage defaultMessage) {
        this.defaultMessage = defaultMessage;
        return this;
    }

    public DefaultPushNotificationMessage getDefaultPushNotificationMessage() {
        return this.defaultPushNotificationMessage;
    }

    public void setDefaultPushNotificationMessage(DefaultPushNotificationMessage defaultPushNotificationMessage) {
        this.defaultPushNotificationMessage = defaultPushNotificationMessage;
    }

    public DirectMessageConfiguration withDefaultPushNotificationMessage(DefaultPushNotificationMessage defaultPushNotificationMessage) {
        this.defaultPushNotificationMessage = defaultPushNotificationMessage;
        return this;
    }

    public EmailMessage getEmailMessage() {
        return this.emailMessage;
    }

    public void setEmailMessage(EmailMessage emailMessage) {
        this.emailMessage = emailMessage;
    }

    public DirectMessageConfiguration withEmailMessage(EmailMessage emailMessage) {
        this.emailMessage = emailMessage;
        return this;
    }

    public GCMMessage getGCMMessage() {
        return this.gCMMessage;
    }

    public void setGCMMessage(GCMMessage gCMMessage) {
        this.gCMMessage = gCMMessage;
    }

    public DirectMessageConfiguration withGCMMessage(GCMMessage gCMMessage) {
        this.gCMMessage = gCMMessage;
        return this;
    }

    public SMSMessage getSMSMessage() {
        return this.sMSMessage;
    }

    public void setSMSMessage(SMSMessage sMSMessage) {
        this.sMSMessage = sMSMessage;
    }

    public DirectMessageConfiguration withSMSMessage(SMSMessage sMSMessage) {
        this.sMSMessage = sMSMessage;
        return this;
    }

    public VoiceMessage getVoiceMessage() {
        return this.voiceMessage;
    }

    public void setVoiceMessage(VoiceMessage voiceMessage) {
        this.voiceMessage = voiceMessage;
    }

    public DirectMessageConfiguration withVoiceMessage(VoiceMessage voiceMessage) {
        this.voiceMessage = voiceMessage;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
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
        if (getDefaultPushNotificationMessage() != null) {
            sb.append("DefaultPushNotificationMessage: " + getDefaultPushNotificationMessage() + ",");
        }
        if (getEmailMessage() != null) {
            sb.append("EmailMessage: " + getEmailMessage() + ",");
        }
        if (getGCMMessage() != null) {
            sb.append("GCMMessage: " + getGCMMessage() + ",");
        }
        if (getSMSMessage() != null) {
            sb.append("SMSMessage: " + getSMSMessage() + ",");
        }
        if (getVoiceMessage() != null) {
            sb.append("VoiceMessage: " + getVoiceMessage());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((((((getADMMessage() == null ? 0 : getADMMessage().hashCode()) + 31) * 31) + (getAPNSMessage() == null ? 0 : getAPNSMessage().hashCode())) * 31) + (getBaiduMessage() == null ? 0 : getBaiduMessage().hashCode())) * 31) + (getDefaultMessage() == null ? 0 : getDefaultMessage().hashCode())) * 31) + (getDefaultPushNotificationMessage() == null ? 0 : getDefaultPushNotificationMessage().hashCode())) * 31) + (getEmailMessage() == null ? 0 : getEmailMessage().hashCode())) * 31) + (getGCMMessage() == null ? 0 : getGCMMessage().hashCode())) * 31) + (getSMSMessage() == null ? 0 : getSMSMessage().hashCode())) * 31) + (getVoiceMessage() != null ? getVoiceMessage().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DirectMessageConfiguration)) {
            return false;
        }
        DirectMessageConfiguration directMessageConfiguration = (DirectMessageConfiguration) obj;
        if ((directMessageConfiguration.getADMMessage() == null) ^ (getADMMessage() == null)) {
            return false;
        }
        if (directMessageConfiguration.getADMMessage() != null && !directMessageConfiguration.getADMMessage().equals(getADMMessage())) {
            return false;
        }
        if ((directMessageConfiguration.getAPNSMessage() == null) ^ (getAPNSMessage() == null)) {
            return false;
        }
        if (directMessageConfiguration.getAPNSMessage() != null && !directMessageConfiguration.getAPNSMessage().equals(getAPNSMessage())) {
            return false;
        }
        if ((directMessageConfiguration.getBaiduMessage() == null) ^ (getBaiduMessage() == null)) {
            return false;
        }
        if (directMessageConfiguration.getBaiduMessage() != null && !directMessageConfiguration.getBaiduMessage().equals(getBaiduMessage())) {
            return false;
        }
        if ((directMessageConfiguration.getDefaultMessage() == null) ^ (getDefaultMessage() == null)) {
            return false;
        }
        if (directMessageConfiguration.getDefaultMessage() != null && !directMessageConfiguration.getDefaultMessage().equals(getDefaultMessage())) {
            return false;
        }
        if ((directMessageConfiguration.getDefaultPushNotificationMessage() == null) ^ (getDefaultPushNotificationMessage() == null)) {
            return false;
        }
        if (directMessageConfiguration.getDefaultPushNotificationMessage() != null && !directMessageConfiguration.getDefaultPushNotificationMessage().equals(getDefaultPushNotificationMessage())) {
            return false;
        }
        if ((directMessageConfiguration.getEmailMessage() == null) ^ (getEmailMessage() == null)) {
            return false;
        }
        if (directMessageConfiguration.getEmailMessage() != null && !directMessageConfiguration.getEmailMessage().equals(getEmailMessage())) {
            return false;
        }
        if ((directMessageConfiguration.getGCMMessage() == null) ^ (getGCMMessage() == null)) {
            return false;
        }
        if (directMessageConfiguration.getGCMMessage() != null && !directMessageConfiguration.getGCMMessage().equals(getGCMMessage())) {
            return false;
        }
        if ((directMessageConfiguration.getSMSMessage() == null) ^ (getSMSMessage() == null)) {
            return false;
        }
        if (directMessageConfiguration.getSMSMessage() != null && !directMessageConfiguration.getSMSMessage().equals(getSMSMessage())) {
            return false;
        }
        if ((directMessageConfiguration.getVoiceMessage() == null) ^ (getVoiceMessage() == null)) {
            return false;
        }
        return directMessageConfiguration.getVoiceMessage() == null || directMessageConfiguration.getVoiceMessage().equals(getVoiceMessage());
    }
}
