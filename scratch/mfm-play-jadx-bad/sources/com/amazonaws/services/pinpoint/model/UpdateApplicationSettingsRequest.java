package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateApplicationSettingsRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private WriteApplicationSettingsRequest writeApplicationSettingsRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public UpdateApplicationSettingsRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public WriteApplicationSettingsRequest getWriteApplicationSettingsRequest() {
        return this.writeApplicationSettingsRequest;
    }

    public void setWriteApplicationSettingsRequest(WriteApplicationSettingsRequest writeApplicationSettingsRequest) {
        this.writeApplicationSettingsRequest = writeApplicationSettingsRequest;
    }

    public UpdateApplicationSettingsRequest withWriteApplicationSettingsRequest(WriteApplicationSettingsRequest writeApplicationSettingsRequest) {
        this.writeApplicationSettingsRequest = writeApplicationSettingsRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getWriteApplicationSettingsRequest() != null) {
            sb.append("WriteApplicationSettingsRequest: " + getWriteApplicationSettingsRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getWriteApplicationSettingsRequest() != null ? getWriteApplicationSettingsRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateApplicationSettingsRequest)) {
            return false;
        }
        UpdateApplicationSettingsRequest updateApplicationSettingsRequest = (UpdateApplicationSettingsRequest) obj;
        if ((updateApplicationSettingsRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (updateApplicationSettingsRequest.getApplicationId() != null && !updateApplicationSettingsRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((updateApplicationSettingsRequest.getWriteApplicationSettingsRequest() == null) ^ (getWriteApplicationSettingsRequest() == null)) {
            return false;
        }
        return updateApplicationSettingsRequest.getWriteApplicationSettingsRequest() == null || updateApplicationSettingsRequest.getWriteApplicationSettingsRequest().equals(getWriteApplicationSettingsRequest());
    }
}
