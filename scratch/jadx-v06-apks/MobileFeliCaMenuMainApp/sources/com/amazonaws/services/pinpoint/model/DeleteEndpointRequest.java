package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DeleteEndpointRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private String endpointId;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public DeleteEndpointRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getEndpointId() {
        return this.endpointId;
    }

    public void setEndpointId(String str) {
        this.endpointId = str;
    }

    public DeleteEndpointRequest withEndpointId(String str) {
        this.endpointId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getEndpointId() != null) {
            sb.append("EndpointId: " + getEndpointId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getEndpointId() != null ? getEndpointId().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DeleteEndpointRequest)) {
            return false;
        }
        DeleteEndpointRequest deleteEndpointRequest = (DeleteEndpointRequest) obj;
        if ((deleteEndpointRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (deleteEndpointRequest.getApplicationId() != null && !deleteEndpointRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((deleteEndpointRequest.getEndpointId() == null) ^ (getEndpointId() == null)) {
            return false;
        }
        return deleteEndpointRequest.getEndpointId() == null || deleteEndpointRequest.getEndpointId().equals(getEndpointId());
    }
}
