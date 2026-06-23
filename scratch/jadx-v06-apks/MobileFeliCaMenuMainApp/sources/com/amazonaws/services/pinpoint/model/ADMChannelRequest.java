package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class ADMChannelRequest implements Serializable {
    private String clientId;
    private String clientSecret;
    private Boolean enabled;

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String str) {
        this.clientId = str;
    }

    public ADMChannelRequest withClientId(String str) {
        this.clientId = str;
        return this;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }

    public void setClientSecret(String str) {
        this.clientSecret = str;
    }

    public ADMChannelRequest withClientSecret(String str) {
        this.clientSecret = str;
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

    public ADMChannelRequest withEnabled(Boolean bool) {
        this.enabled = bool;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getClientId() != null) {
            sb.append("ClientId: " + getClientId() + ",");
        }
        if (getClientSecret() != null) {
            sb.append("ClientSecret: " + getClientSecret() + ",");
        }
        if (getEnabled() != null) {
            sb.append("Enabled: " + getEnabled());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((getClientId() == null ? 0 : getClientId().hashCode()) + 31) * 31) + (getClientSecret() == null ? 0 : getClientSecret().hashCode())) * 31) + (getEnabled() != null ? getEnabled().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ADMChannelRequest)) {
            return false;
        }
        ADMChannelRequest aDMChannelRequest = (ADMChannelRequest) obj;
        if ((aDMChannelRequest.getClientId() == null) ^ (getClientId() == null)) {
            return false;
        }
        if (aDMChannelRequest.getClientId() != null && !aDMChannelRequest.getClientId().equals(getClientId())) {
            return false;
        }
        if ((aDMChannelRequest.getClientSecret() == null) ^ (getClientSecret() == null)) {
            return false;
        }
        if (aDMChannelRequest.getClientSecret() != null && !aDMChannelRequest.getClientSecret().equals(getClientSecret())) {
            return false;
        }
        if ((aDMChannelRequest.getEnabled() == null) ^ (getEnabled() == null)) {
            return false;
        }
        return aDMChannelRequest.getEnabled() == null || aDMChannelRequest.getEnabled().equals(getEnabled());
    }
}
