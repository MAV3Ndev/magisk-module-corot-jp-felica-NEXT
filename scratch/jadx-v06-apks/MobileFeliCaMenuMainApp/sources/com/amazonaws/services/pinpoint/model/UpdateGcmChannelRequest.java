package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateGcmChannelRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private GCMChannelRequest gCMChannelRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public UpdateGcmChannelRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public GCMChannelRequest getGCMChannelRequest() {
        return this.gCMChannelRequest;
    }

    public void setGCMChannelRequest(GCMChannelRequest gCMChannelRequest) {
        this.gCMChannelRequest = gCMChannelRequest;
    }

    public UpdateGcmChannelRequest withGCMChannelRequest(GCMChannelRequest gCMChannelRequest) {
        this.gCMChannelRequest = gCMChannelRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getGCMChannelRequest() != null) {
            sb.append("GCMChannelRequest: " + getGCMChannelRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getGCMChannelRequest() != null ? getGCMChannelRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateGcmChannelRequest)) {
            return false;
        }
        UpdateGcmChannelRequest updateGcmChannelRequest = (UpdateGcmChannelRequest) obj;
        if ((updateGcmChannelRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (updateGcmChannelRequest.getApplicationId() != null && !updateGcmChannelRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((updateGcmChannelRequest.getGCMChannelRequest() == null) ^ (getGCMChannelRequest() == null)) {
            return false;
        }
        return updateGcmChannelRequest.getGCMChannelRequest() == null || updateGcmChannelRequest.getGCMChannelRequest().equals(getGCMChannelRequest());
    }
}
