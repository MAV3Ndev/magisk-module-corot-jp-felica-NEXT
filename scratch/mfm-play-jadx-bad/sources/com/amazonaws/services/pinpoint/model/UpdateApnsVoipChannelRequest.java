package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateApnsVoipChannelRequest extends AmazonWebServiceRequest implements Serializable {
    private APNSVoipChannelRequest aPNSVoipChannelRequest;
    private String applicationId;

    public APNSVoipChannelRequest getAPNSVoipChannelRequest() {
        return this.aPNSVoipChannelRequest;
    }

    public void setAPNSVoipChannelRequest(APNSVoipChannelRequest aPNSVoipChannelRequest) {
        this.aPNSVoipChannelRequest = aPNSVoipChannelRequest;
    }

    public UpdateApnsVoipChannelRequest withAPNSVoipChannelRequest(APNSVoipChannelRequest aPNSVoipChannelRequest) {
        this.aPNSVoipChannelRequest = aPNSVoipChannelRequest;
        return this;
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public UpdateApnsVoipChannelRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getAPNSVoipChannelRequest() != null) {
            sb.append("APNSVoipChannelRequest: " + getAPNSVoipChannelRequest() + ",");
        }
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getAPNSVoipChannelRequest() == null ? 0 : getAPNSVoipChannelRequest().hashCode()) + 31) * 31) + (getApplicationId() != null ? getApplicationId().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateApnsVoipChannelRequest)) {
            return false;
        }
        UpdateApnsVoipChannelRequest updateApnsVoipChannelRequest = (UpdateApnsVoipChannelRequest) obj;
        if ((updateApnsVoipChannelRequest.getAPNSVoipChannelRequest() == null) ^ (getAPNSVoipChannelRequest() == null)) {
            return false;
        }
        if (updateApnsVoipChannelRequest.getAPNSVoipChannelRequest() != null && !updateApnsVoipChannelRequest.getAPNSVoipChannelRequest().equals(getAPNSVoipChannelRequest())) {
            return false;
        }
        if ((updateApnsVoipChannelRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        return updateApnsVoipChannelRequest.getApplicationId() == null || updateApnsVoipChannelRequest.getApplicationId().equals(getApplicationId());
    }
}
