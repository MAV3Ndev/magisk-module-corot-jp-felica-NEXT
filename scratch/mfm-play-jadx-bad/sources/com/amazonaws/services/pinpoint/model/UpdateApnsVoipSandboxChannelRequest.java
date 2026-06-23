package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateApnsVoipSandboxChannelRequest extends AmazonWebServiceRequest implements Serializable {
    private APNSVoipSandboxChannelRequest aPNSVoipSandboxChannelRequest;
    private String applicationId;

    public APNSVoipSandboxChannelRequest getAPNSVoipSandboxChannelRequest() {
        return this.aPNSVoipSandboxChannelRequest;
    }

    public void setAPNSVoipSandboxChannelRequest(APNSVoipSandboxChannelRequest aPNSVoipSandboxChannelRequest) {
        this.aPNSVoipSandboxChannelRequest = aPNSVoipSandboxChannelRequest;
    }

    public UpdateApnsVoipSandboxChannelRequest withAPNSVoipSandboxChannelRequest(APNSVoipSandboxChannelRequest aPNSVoipSandboxChannelRequest) {
        this.aPNSVoipSandboxChannelRequest = aPNSVoipSandboxChannelRequest;
        return this;
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public UpdateApnsVoipSandboxChannelRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getAPNSVoipSandboxChannelRequest() != null) {
            sb.append("APNSVoipSandboxChannelRequest: " + getAPNSVoipSandboxChannelRequest() + ",");
        }
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getAPNSVoipSandboxChannelRequest() == null ? 0 : getAPNSVoipSandboxChannelRequest().hashCode()) + 31) * 31) + (getApplicationId() != null ? getApplicationId().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateApnsVoipSandboxChannelRequest)) {
            return false;
        }
        UpdateApnsVoipSandboxChannelRequest updateApnsVoipSandboxChannelRequest = (UpdateApnsVoipSandboxChannelRequest) obj;
        if ((updateApnsVoipSandboxChannelRequest.getAPNSVoipSandboxChannelRequest() == null) ^ (getAPNSVoipSandboxChannelRequest() == null)) {
            return false;
        }
        if (updateApnsVoipSandboxChannelRequest.getAPNSVoipSandboxChannelRequest() != null && !updateApnsVoipSandboxChannelRequest.getAPNSVoipSandboxChannelRequest().equals(getAPNSVoipSandboxChannelRequest())) {
            return false;
        }
        if ((updateApnsVoipSandboxChannelRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        return updateApnsVoipSandboxChannelRequest.getApplicationId() == null || updateApnsVoipSandboxChannelRequest.getApplicationId().equals(getApplicationId());
    }
}
