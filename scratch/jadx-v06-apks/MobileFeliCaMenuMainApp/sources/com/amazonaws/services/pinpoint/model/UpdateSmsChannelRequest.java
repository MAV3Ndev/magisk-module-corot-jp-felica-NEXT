package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateSmsChannelRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private SMSChannelRequest sMSChannelRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public UpdateSmsChannelRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public SMSChannelRequest getSMSChannelRequest() {
        return this.sMSChannelRequest;
    }

    public void setSMSChannelRequest(SMSChannelRequest sMSChannelRequest) {
        this.sMSChannelRequest = sMSChannelRequest;
    }

    public UpdateSmsChannelRequest withSMSChannelRequest(SMSChannelRequest sMSChannelRequest) {
        this.sMSChannelRequest = sMSChannelRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getSMSChannelRequest() != null) {
            sb.append("SMSChannelRequest: " + getSMSChannelRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getSMSChannelRequest() != null ? getSMSChannelRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateSmsChannelRequest)) {
            return false;
        }
        UpdateSmsChannelRequest updateSmsChannelRequest = (UpdateSmsChannelRequest) obj;
        if ((updateSmsChannelRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (updateSmsChannelRequest.getApplicationId() != null && !updateSmsChannelRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((updateSmsChannelRequest.getSMSChannelRequest() == null) ^ (getSMSChannelRequest() == null)) {
            return false;
        }
        return updateSmsChannelRequest.getSMSChannelRequest() == null || updateSmsChannelRequest.getSMSChannelRequest().equals(getSMSChannelRequest());
    }
}
