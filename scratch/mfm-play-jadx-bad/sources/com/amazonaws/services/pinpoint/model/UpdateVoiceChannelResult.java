package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateVoiceChannelResult implements Serializable {
    private VoiceChannelResponse voiceChannelResponse;

    public VoiceChannelResponse getVoiceChannelResponse() {
        return this.voiceChannelResponse;
    }

    public void setVoiceChannelResponse(VoiceChannelResponse voiceChannelResponse) {
        this.voiceChannelResponse = voiceChannelResponse;
    }

    public UpdateVoiceChannelResult withVoiceChannelResponse(VoiceChannelResponse voiceChannelResponse) {
        this.voiceChannelResponse = voiceChannelResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getVoiceChannelResponse() != null) {
            sb.append("VoiceChannelResponse: " + getVoiceChannelResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getVoiceChannelResponse() == null ? 0 : getVoiceChannelResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateVoiceChannelResult)) {
            return false;
        }
        UpdateVoiceChannelResult updateVoiceChannelResult = (UpdateVoiceChannelResult) obj;
        if ((updateVoiceChannelResult.getVoiceChannelResponse() == null) ^ (getVoiceChannelResponse() == null)) {
            return false;
        }
        return updateVoiceChannelResult.getVoiceChannelResponse() == null || updateVoiceChannelResult.getVoiceChannelResponse().equals(getVoiceChannelResponse());
    }
}
