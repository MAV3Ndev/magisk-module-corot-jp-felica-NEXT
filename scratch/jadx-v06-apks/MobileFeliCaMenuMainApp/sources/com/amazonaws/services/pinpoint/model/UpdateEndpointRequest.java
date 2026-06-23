package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateEndpointRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private String endpointId;
    private EndpointRequest endpointRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public UpdateEndpointRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getEndpointId() {
        return this.endpointId;
    }

    public void setEndpointId(String str) {
        this.endpointId = str;
    }

    public UpdateEndpointRequest withEndpointId(String str) {
        this.endpointId = str;
        return this;
    }

    public EndpointRequest getEndpointRequest() {
        return this.endpointRequest;
    }

    public void setEndpointRequest(EndpointRequest endpointRequest) {
        this.endpointRequest = endpointRequest;
    }

    public UpdateEndpointRequest withEndpointRequest(EndpointRequest endpointRequest) {
        this.endpointRequest = endpointRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getEndpointId() != null) {
            sb.append("EndpointId: " + getEndpointId() + ",");
        }
        if (getEndpointRequest() != null) {
            sb.append("EndpointRequest: " + getEndpointRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getEndpointId() == null ? 0 : getEndpointId().hashCode())) * 31) + (getEndpointRequest() != null ? getEndpointRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateEndpointRequest)) {
            return false;
        }
        UpdateEndpointRequest updateEndpointRequest = (UpdateEndpointRequest) obj;
        if ((updateEndpointRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (updateEndpointRequest.getApplicationId() != null && !updateEndpointRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((updateEndpointRequest.getEndpointId() == null) ^ (getEndpointId() == null)) {
            return false;
        }
        if (updateEndpointRequest.getEndpointId() != null && !updateEndpointRequest.getEndpointId().equals(getEndpointId())) {
            return false;
        }
        if ((updateEndpointRequest.getEndpointRequest() == null) ^ (getEndpointRequest() == null)) {
            return false;
        }
        return updateEndpointRequest.getEndpointRequest() == null || updateEndpointRequest.getEndpointRequest().equals(getEndpointRequest());
    }
}
