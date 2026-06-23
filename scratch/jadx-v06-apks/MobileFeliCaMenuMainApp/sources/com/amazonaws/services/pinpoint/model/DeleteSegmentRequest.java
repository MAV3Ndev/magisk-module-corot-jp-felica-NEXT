package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DeleteSegmentRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private String segmentId;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public DeleteSegmentRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getSegmentId() {
        return this.segmentId;
    }

    public void setSegmentId(String str) {
        this.segmentId = str;
    }

    public DeleteSegmentRequest withSegmentId(String str) {
        this.segmentId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getSegmentId() != null) {
            sb.append("SegmentId: " + getSegmentId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getSegmentId() != null ? getSegmentId().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DeleteSegmentRequest)) {
            return false;
        }
        DeleteSegmentRequest deleteSegmentRequest = (DeleteSegmentRequest) obj;
        if ((deleteSegmentRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (deleteSegmentRequest.getApplicationId() != null && !deleteSegmentRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((deleteSegmentRequest.getSegmentId() == null) ^ (getSegmentId() == null)) {
            return false;
        }
        return deleteSegmentRequest.getSegmentId() == null || deleteSegmentRequest.getSegmentId().equals(getSegmentId());
    }
}
