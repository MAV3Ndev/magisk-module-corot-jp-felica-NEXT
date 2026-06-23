package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateEndpointsBatchRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private EndpointBatchRequest endpointBatchRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public UpdateEndpointsBatchRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public EndpointBatchRequest getEndpointBatchRequest() {
        return this.endpointBatchRequest;
    }

    public void setEndpointBatchRequest(EndpointBatchRequest endpointBatchRequest) {
        this.endpointBatchRequest = endpointBatchRequest;
    }

    public UpdateEndpointsBatchRequest withEndpointBatchRequest(EndpointBatchRequest endpointBatchRequest) {
        this.endpointBatchRequest = endpointBatchRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getEndpointBatchRequest() != null) {
            sb.append("EndpointBatchRequest: " + getEndpointBatchRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getEndpointBatchRequest() != null ? getEndpointBatchRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateEndpointsBatchRequest)) {
            return false;
        }
        UpdateEndpointsBatchRequest updateEndpointsBatchRequest = (UpdateEndpointsBatchRequest) obj;
        if ((updateEndpointsBatchRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (updateEndpointsBatchRequest.getApplicationId() != null && !updateEndpointsBatchRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((updateEndpointsBatchRequest.getEndpointBatchRequest() == null) ^ (getEndpointBatchRequest() == null)) {
            return false;
        }
        return updateEndpointsBatchRequest.getEndpointBatchRequest() == null || updateEndpointsBatchRequest.getEndpointBatchRequest().equals(getEndpointBatchRequest());
    }
}
