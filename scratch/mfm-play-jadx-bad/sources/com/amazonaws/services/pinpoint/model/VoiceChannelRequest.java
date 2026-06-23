package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class VoiceChannelRequest implements Serializable {
    private Boolean enabled;

    public Boolean isEnabled() {
        return this.enabled;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean bool) {
        this.enabled = bool;
    }

    public VoiceChannelRequest withEnabled(Boolean bool) {
        this.enabled = bool;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getEnabled() != null) {
            sb.append("Enabled: " + getEnabled());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getEnabled() == null ? 0 : getEnabled().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof VoiceChannelRequest)) {
            return false;
        }
        VoiceChannelRequest voiceChannelRequest = (VoiceChannelRequest) obj;
        if ((voiceChannelRequest.getEnabled() == null) ^ (getEnabled() == null)) {
            return false;
        }
        return voiceChannelRequest.getEnabled() == null || voiceChannelRequest.getEnabled().equals(getEnabled());
    }
}
