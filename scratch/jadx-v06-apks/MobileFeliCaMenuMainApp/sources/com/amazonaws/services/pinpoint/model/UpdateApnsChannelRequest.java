package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateApnsChannelRequest extends AmazonWebServiceRequest implements Serializable {
    private APNSChannelRequest aPNSChannelRequest;
    private String applicationId;

    public APNSChannelRequest getAPNSChannelRequest() {
        return this.aPNSChannelRequest;
    }

    public void setAPNSChannelRequest(APNSChannelRequest aPNSChannelRequest) {
        this.aPNSChannelRequest = aPNSChannelRequest;
    }

    public UpdateApnsChannelRequest withAPNSChannelRequest(APNSChannelRequest aPNSChannelRequest) {
        this.aPNSChannelRequest = aPNSChannelRequest;
        return this;
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public UpdateApnsChannelRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAPNSChannelRequest() != null) {
            sb.append("APNSChannelRequest: " + getAPNSChannelRequest() + ",");
        }
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getAPNSChannelRequest() == null ? 0 : getAPNSChannelRequest().hashCode()) + 31) * 31) + (getApplicationId() != null ? getApplicationId().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateApnsChannelRequest)) {
            return false;
        }
        UpdateApnsChannelRequest updateApnsChannelRequest = (UpdateApnsChannelRequest) obj;
        if ((updateApnsChannelRequest.getAPNSChannelRequest() == null) ^ (getAPNSChannelRequest() == null)) {
            return false;
        }
        if (updateApnsChannelRequest.getAPNSChannelRequest() != null && !updateApnsChannelRequest.getAPNSChannelRequest().equals(getAPNSChannelRequest())) {
            return false;
        }
        if ((updateApnsChannelRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        return updateApnsChannelRequest.getApplicationId() == null || updateApnsChannelRequest.getApplicationId().equals(getApplicationId());
    }
}
