package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateVoiceChannelRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private VoiceChannelRequest voiceChannelRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public UpdateVoiceChannelRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public VoiceChannelRequest getVoiceChannelRequest() {
        return this.voiceChannelRequest;
    }

    public void setVoiceChannelRequest(VoiceChannelRequest voiceChannelRequest) {
        this.voiceChannelRequest = voiceChannelRequest;
    }

    public UpdateVoiceChannelRequest withVoiceChannelRequest(VoiceChannelRequest voiceChannelRequest) {
        this.voiceChannelRequest = voiceChannelRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getVoiceChannelRequest() != null) {
            sb.append("VoiceChannelRequest: " + getVoiceChannelRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getVoiceChannelRequest() != null ? getVoiceChannelRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateVoiceChannelRequest)) {
            return false;
        }
        UpdateVoiceChannelRequest updateVoiceChannelRequest = (UpdateVoiceChannelRequest) obj;
        if ((updateVoiceChannelRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (updateVoiceChannelRequest.getApplicationId() != null && !updateVoiceChannelRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((updateVoiceChannelRequest.getVoiceChannelRequest() == null) ^ (getVoiceChannelRequest() == null)) {
            return false;
        }
        return updateVoiceChannelRequest.getVoiceChannelRequest() == null || updateVoiceChannelRequest.getVoiceChannelRequest().equals(getVoiceChannelRequest());
    }
}
