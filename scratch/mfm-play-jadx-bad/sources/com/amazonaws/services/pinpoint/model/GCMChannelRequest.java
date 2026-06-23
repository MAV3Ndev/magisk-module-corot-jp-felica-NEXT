package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GCMChannelRequest implements Serializable {
    private String apiKey;
    private Boolean enabled;

    public String getApiKey() {
        return this.apiKey;
    }

    public void setApiKey(String str) {
        this.apiKey = str;
    }

    public GCMChannelRequest withApiKey(String str) {
        this.apiKey = str;
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

    public GCMChannelRequest withEnabled(Boolean bool) {
        this.enabled = bool;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getApiKey() != null) {
            sb.append("ApiKey: " + getApiKey() + ",");
        }
        if (getEnabled() != null) {
            sb.append("Enabled: " + getEnabled());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApiKey() == null ? 0 : getApiKey().hashCode()) + 31) * 31) + (getEnabled() != null ? getEnabled().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GCMChannelRequest)) {
            return false;
        }
        GCMChannelRequest gCMChannelRequest = (GCMChannelRequest) obj;
        if ((gCMChannelRequest.getApiKey() == null) ^ (getApiKey() == null)) {
            return false;
        }
        if (gCMChannelRequest.getApiKey() != null && !gCMChannelRequest.getApiKey().equals(getApiKey())) {
            return false;
        }
        if ((gCMChannelRequest.getEnabled() == null) ^ (getEnabled() == null)) {
            return false;
        }
        return gCMChannelRequest.getEnabled() == null || gCMChannelRequest.getEnabled().equals(getEnabled());
    }
}
