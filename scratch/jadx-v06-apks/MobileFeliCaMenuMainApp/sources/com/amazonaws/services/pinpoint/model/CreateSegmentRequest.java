package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CreateSegmentRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private WriteSegmentRequest writeSegmentRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public CreateSegmentRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public WriteSegmentRequest getWriteSegmentRequest() {
        return this.writeSegmentRequest;
    }

    public void setWriteSegmentRequest(WriteSegmentRequest writeSegmentRequest) {
        this.writeSegmentRequest = writeSegmentRequest;
    }

    public CreateSegmentRequest withWriteSegmentRequest(WriteSegmentRequest writeSegmentRequest) {
        this.writeSegmentRequest = writeSegmentRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getWriteSegmentRequest() != null) {
            sb.append("WriteSegmentRequest: " + getWriteSegmentRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getWriteSegmentRequest() != null ? getWriteSegmentRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CreateSegmentRequest)) {
            return false;
        }
        CreateSegmentRequest createSegmentRequest = (CreateSegmentRequest) obj;
        if ((createSegmentRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (createSegmentRequest.getApplicationId() != null && !createSegmentRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((createSegmentRequest.getWriteSegmentRequest() == null) ^ (getWriteSegmentRequest() == null)) {
            return false;
        }
        return createSegmentRequest.getWriteSegmentRequest() == null || createSegmentRequest.getWriteSegmentRequest().equals(getWriteSegmentRequest());
    }
}
