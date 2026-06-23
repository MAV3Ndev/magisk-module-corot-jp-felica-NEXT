package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetEndpointRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private String endpointId;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public GetEndpointRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getEndpointId() {
        return this.endpointId;
    }

    public void setEndpointId(String str) {
        this.endpointId = str;
    }

    public GetEndpointRequest withEndpointId(String str) {
        this.endpointId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
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
        if (obj == null || !(obj instanceof GetEndpointRequest)) {
            return false;
        }
        GetEndpointRequest getEndpointRequest = (GetEndpointRequest) obj;
        if ((getEndpointRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (getEndpointRequest.getApplicationId() != null && !getEndpointRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((getEndpointRequest.getEndpointId() == null) ^ (getEndpointId() == null)) {
            return false;
        }
        return getEndpointRequest.getEndpointId() == null || getEndpointRequest.getEndpointId().equals(getEndpointId());
    }
}
