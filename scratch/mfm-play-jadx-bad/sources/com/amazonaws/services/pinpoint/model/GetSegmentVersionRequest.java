package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentVersionRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private String segmentId;
    private String version;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public GetSegmentVersionRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getSegmentId() {
        return this.segmentId;
    }

    public void setSegmentId(String str) {
        this.segmentId = str;
    }

    public GetSegmentVersionRequest withSegmentId(String str) {
        this.segmentId = str;
        return this;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public GetSegmentVersionRequest withVersion(String str) {
        this.version = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getSegmentId() != null) {
            sb.append("SegmentId: " + getSegmentId() + ",");
        }
        if (getVersion() != null) {
            sb.append("Version: " + getVersion());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getSegmentId() == null ? 0 : getSegmentId().hashCode())) * 31) + (getVersion() != null ? getVersion().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetSegmentVersionRequest)) {
            return false;
        }
        GetSegmentVersionRequest getSegmentVersionRequest = (GetSegmentVersionRequest) obj;
        if ((getSegmentVersionRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (getSegmentVersionRequest.getApplicationId() != null && !getSegmentVersionRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((getSegmentVersionRequest.getSegmentId() == null) ^ (getSegmentId() == null)) {
            return false;
        }
        if (getSegmentVersionRequest.getSegmentId() != null && !getSegmentVersionRequest.getSegmentId().equals(getSegmentId())) {
            return false;
        }
        if ((getSegmentVersionRequest.getVersion() == null) ^ (getVersion() == null)) {
            return false;
        }
        return getSegmentVersionRequest.getVersion() == null || getSegmentVersionRequest.getVersion().equals(getVersion());
    }
}
