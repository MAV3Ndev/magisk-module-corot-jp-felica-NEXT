package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SMSChannelRequest implements Serializable {
    private Boolean enabled;
    private String senderId;
    private String shortCode;

    public Boolean isEnabled() {
        return this.enabled;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean bool) {
        this.enabled = bool;
    }

    public SMSChannelRequest withEnabled(Boolean bool) {
        this.enabled = bool;
        return this;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public void setSenderId(String str) {
        this.senderId = str;
    }

    public SMSChannelRequest withSenderId(String str) {
        this.senderId = str;
        return this;
    }

    public String getShortCode() {
        return this.shortCode;
    }

    public void setShortCode(String str) {
        this.shortCode = str;
    }

    public SMSChannelRequest withShortCode(String str) {
        this.shortCode = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getEnabled() != null) {
            sb.append("Enabled: " + getEnabled() + ",");
        }
        if (getSenderId() != null) {
            sb.append("SenderId: " + getSenderId() + ",");
        }
        if (getShortCode() != null) {
            sb.append("ShortCode: " + getShortCode());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((getEnabled() == null ? 0 : getEnabled().hashCode()) + 31) * 31) + (getSenderId() == null ? 0 : getSenderId().hashCode())) * 31) + (getShortCode() != null ? getShortCode().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SMSChannelRequest)) {
            return false;
        }
        SMSChannelRequest sMSChannelRequest = (SMSChannelRequest) obj;
        if ((sMSChannelRequest.getEnabled() == null) ^ (getEnabled() == null)) {
            return false;
        }
        if (sMSChannelRequest.getEnabled() != null && !sMSChannelRequest.getEnabled().equals(getEnabled())) {
            return false;
        }
        if ((sMSChannelRequest.getSenderId() == null) ^ (getSenderId() == null)) {
            return false;
        }
        if (sMSChannelRequest.getSenderId() != null && !sMSChannelRequest.getSenderId().equals(getSenderId())) {
            return false;
        }
        if ((sMSChannelRequest.getShortCode() == null) ^ (getShortCode() == null)) {
            return false;
        }
        return sMSChannelRequest.getShortCode() == null || sMSChannelRequest.getShortCode().equals(getShortCode());
    }
}
