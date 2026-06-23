package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateAdmChannelRequest extends AmazonWebServiceRequest implements Serializable {
    private ADMChannelRequest aDMChannelRequest;
    private String applicationId;

    public ADMChannelRequest getADMChannelRequest() {
        return this.aDMChannelRequest;
    }

    public void setADMChannelRequest(ADMChannelRequest aDMChannelRequest) {
        this.aDMChannelRequest = aDMChannelRequest;
    }

    public UpdateAdmChannelRequest withADMChannelRequest(ADMChannelRequest aDMChannelRequest) {
        this.aDMChannelRequest = aDMChannelRequest;
        return this;
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public UpdateAdmChannelRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getADMChannelRequest() != null) {
            sb.append("ADMChannelRequest: " + getADMChannelRequest() + ",");
        }
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getADMChannelRequest() == null ? 0 : getADMChannelRequest().hashCode()) + 31) * 31) + (getApplicationId() != null ? getApplicationId().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateAdmChannelRequest)) {
            return false;
        }
        UpdateAdmChannelRequest updateAdmChannelRequest = (UpdateAdmChannelRequest) obj;
        if ((updateAdmChannelRequest.getADMChannelRequest() == null) ^ (getADMChannelRequest() == null)) {
            return false;
        }
        if (updateAdmChannelRequest.getADMChannelRequest() != null && !updateAdmChannelRequest.getADMChannelRequest().equals(getADMChannelRequest())) {
            return false;
        }
        if ((updateAdmChannelRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        return updateAdmChannelRequest.getApplicationId() == null || updateAdmChannelRequest.getApplicationId().equals(getApplicationId());
    }
}
