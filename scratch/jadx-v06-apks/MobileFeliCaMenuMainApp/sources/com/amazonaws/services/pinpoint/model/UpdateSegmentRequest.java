package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateSegmentRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private String segmentId;
    private WriteSegmentRequest writeSegmentRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public UpdateSegmentRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getSegmentId() {
        return this.segmentId;
    }

    public void setSegmentId(String str) {
        this.segmentId = str;
    }

    public UpdateSegmentRequest withSegmentId(String str) {
        this.segmentId = str;
        return this;
    }

    public WriteSegmentRequest getWriteSegmentRequest() {
        return this.writeSegmentRequest;
    }

    public void setWriteSegmentRequest(WriteSegmentRequest writeSegmentRequest) {
        this.writeSegmentRequest = writeSegmentRequest;
    }

    public UpdateSegmentRequest withWriteSegmentRequest(WriteSegmentRequest writeSegmentRequest) {
        this.writeSegmentRequest = writeSegmentRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getSegmentId() != null) {
            sb.append("SegmentId: " + getSegmentId() + ",");
        }
        if (getWriteSegmentRequest() != null) {
            sb.append("WriteSegmentRequest: " + getWriteSegmentRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getSegmentId() == null ? 0 : getSegmentId().hashCode())) * 31) + (getWriteSegmentRequest() != null ? getWriteSegmentRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateSegmentRequest)) {
            return false;
        }
        UpdateSegmentRequest updateSegmentRequest = (UpdateSegmentRequest) obj;
        if ((updateSegmentRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (updateSegmentRequest.getApplicationId() != null && !updateSegmentRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((updateSegmentRequest.getSegmentId() == null) ^ (getSegmentId() == null)) {
            return false;
        }
        if (updateSegmentRequest.getSegmentId() != null && !updateSegmentRequest.getSegmentId().equals(getSegmentId())) {
            return false;
        }
        if ((updateSegmentRequest.getWriteSegmentRequest() == null) ^ (getWriteSegmentRequest() == null)) {
            return false;
        }
        return updateSegmentRequest.getWriteSegmentRequest() == null || updateSegmentRequest.getWriteSegmentRequest().equals(getWriteSegmentRequest());
    }
}
