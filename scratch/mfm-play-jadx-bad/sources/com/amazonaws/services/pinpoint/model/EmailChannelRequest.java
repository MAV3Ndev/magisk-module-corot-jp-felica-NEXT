package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class EmailChannelRequest implements Serializable {
    private String configurationSet;
    private Boolean enabled;
    private String fromAddress;
    private String identity;
    private String roleArn;

    public String getConfigurationSet() {
        return this.configurationSet;
    }

    public void setConfigurationSet(String str) {
        this.configurationSet = str;
    }

    public EmailChannelRequest withConfigurationSet(String str) {
        this.configurationSet = str;
        return this;
    }

    public Boolean isEnabled() {
        return this.enabled;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean bool) {
        this.enabled = bool;
    }

    public EmailChannelRequest withEnabled(Boolean bool) {
        this.enabled = bool;
        return this;
    }

    public String getFromAddress() {
        return this.fromAddress;
    }

    public void setFromAddress(String str) {
        this.fromAddress = str;
    }

    public EmailChannelRequest withFromAddress(String str) {
        this.fromAddress = str;
        return this;
    }

    public String getIdentity() {
        return this.identity;
    }

    public void setIdentity(String str) {
        this.identity = str;
    }

    public EmailChannelRequest withIdentity(String str) {
        this.identity = str;
        return this;
    }

    public String getRoleArn() {
        return this.roleArn;
    }

    public void setRoleArn(String str) {
        this.roleArn = str;
    }

    public EmailChannelRequest withRoleArn(String str) {
        this.roleArn = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getConfigurationSet() != null) {
            sb.append("ConfigurationSet: " + getConfigurationSet() + ",");
        }
        if (getEnabled() != null) {
            sb.append("Enabled: " + getEnabled() + ",");
        }
        if (getFromAddress() != null) {
            sb.append("FromAddress: " + getFromAddress() + ",");
        }
        if (getIdentity() != null) {
            sb.append("Identity: " + getIdentity() + ",");
        }
        if (getRoleArn() != null) {
            sb.append("RoleArn: " + getRoleArn());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((getConfigurationSet() == null ? 0 : getConfigurationSet().hashCode()) + 31) * 31) + (getEnabled() == null ? 0 : getEnabled().hashCode())) * 31) + (getFromAddress() == null ? 0 : getFromAddress().hashCode())) * 31) + (getIdentity() == null ? 0 : getIdentity().hashCode())) * 31) + (getRoleArn() != null ? getRoleArn().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EmailChannelRequest)) {
            return false;
        }
        EmailChannelRequest emailChannelRequest = (EmailChannelRequest) obj;
        if ((emailChannelRequest.getConfigurationSet() == null) ^ (getConfigurationSet() == null)) {
            return false;
        }
        if (emailChannelRequest.getConfigurationSet() != null && !emailChannelRequest.getConfigurationSet().equals(getConfigurationSet())) {
            return false;
        }
        if ((emailChannelRequest.getEnabled() == null) ^ (getEnabled() == null)) {
            return false;
        }
        if (emailChannelRequest.getEnabled() != null && !emailChannelRequest.getEnabled().equals(getEnabled())) {
            return false;
        }
        if ((emailChannelRequest.getFromAddress() == null) ^ (getFromAddress() == null)) {
            return false;
        }
        if (emailChannelRequest.getFromAddress() != null && !emailChannelRequest.getFromAddress().equals(getFromAddress())) {
            return false;
        }
        if ((emailChannelRequest.getIdentity() == null) ^ (getIdentity() == null)) {
            return false;
        }
        if (emailChannelRequest.getIdentity() != null && !emailChannelRequest.getIdentity().equals(getIdentity())) {
            return false;
        }
        if ((emailChannelRequest.getRoleArn() == null) ^ (getRoleArn() == null)) {
            return false;
        }
        return emailChannelRequest.getRoleArn() == null || emailChannelRequest.getRoleArn().equals(getRoleArn());
    }
}
