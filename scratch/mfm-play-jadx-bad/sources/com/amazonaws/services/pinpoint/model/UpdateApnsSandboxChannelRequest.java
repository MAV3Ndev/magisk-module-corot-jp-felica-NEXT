package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateApnsSandboxChannelRequest extends AmazonWebServiceRequest implements Serializable {
    private APNSSandboxChannelRequest aPNSSandboxChannelRequest;
    private String applicationId;

    public APNSSandboxChannelRequest getAPNSSandboxChannelRequest() {
        return this.aPNSSandboxChannelRequest;
    }

    public void setAPNSSandboxChannelRequest(APNSSandboxChannelRequest aPNSSandboxChannelRequest) {
        this.aPNSSandboxChannelRequest = aPNSSandboxChannelRequest;
    }

    public UpdateApnsSandboxChannelRequest withAPNSSandboxChannelRequest(APNSSandboxChannelRequest aPNSSandboxChannelRequest) {
        this.aPNSSandboxChannelRequest = aPNSSandboxChannelRequest;
        return this;
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public UpdateApnsSandboxChannelRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getAPNSSandboxChannelRequest() != null) {
            sb.append("APNSSandboxChannelRequest: " + getAPNSSandboxChannelRequest() + ",");
        }
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getAPNSSandboxChannelRequest() == null ? 0 : getAPNSSandboxChannelRequest().hashCode()) + 31) * 31) + (getApplicationId() != null ? getApplicationId().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateApnsSandboxChannelRequest)) {
            return false;
        }
        UpdateApnsSandboxChannelRequest updateApnsSandboxChannelRequest = (UpdateApnsSandboxChannelRequest) obj;
        if ((updateApnsSandboxChannelRequest.getAPNSSandboxChannelRequest() == null) ^ (getAPNSSandboxChannelRequest() == null)) {
            return false;
        }
        if (updateApnsSandboxChannelRequest.getAPNSSandboxChannelRequest() != null && !updateApnsSandboxChannelRequest.getAPNSSandboxChannelRequest().equals(getAPNSSandboxChannelRequest())) {
            return false;
        }
        if ((updateApnsSandboxChannelRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        return updateApnsSandboxChannelRequest.getApplicationId() == null || updateApnsSandboxChannelRequest.getApplicationId().equals(getApplicationId());
    }
}
