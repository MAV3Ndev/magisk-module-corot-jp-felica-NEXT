package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentImportJobsRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private String pageSize;
    private String segmentId;
    private String token;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public GetSegmentImportJobsRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(String str) {
        this.pageSize = str;
    }

    public GetSegmentImportJobsRequest withPageSize(String str) {
        this.pageSize = str;
        return this;
    }

    public String getSegmentId() {
        return this.segmentId;
    }

    public void setSegmentId(String str) {
        this.segmentId = str;
    }

    public GetSegmentImportJobsRequest withSegmentId(String str) {
        this.segmentId = str;
        return this;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public GetSegmentImportJobsRequest withToken(String str) {
        this.token = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getPageSize() != null) {
            sb.append("PageSize: " + getPageSize() + ",");
        }
        if (getSegmentId() != null) {
            sb.append("SegmentId: " + getSegmentId() + ",");
        }
        if (getToken() != null) {
            sb.append("Token: " + getToken());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getPageSize() == null ? 0 : getPageSize().hashCode())) * 31) + (getSegmentId() == null ? 0 : getSegmentId().hashCode())) * 31) + (getToken() != null ? getToken().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetSegmentImportJobsRequest)) {
            return false;
        }
        GetSegmentImportJobsRequest getSegmentImportJobsRequest = (GetSegmentImportJobsRequest) obj;
        if ((getSegmentImportJobsRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (getSegmentImportJobsRequest.getApplicationId() != null && !getSegmentImportJobsRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((getSegmentImportJobsRequest.getPageSize() == null) ^ (getPageSize() == null)) {
            return false;
        }
        if (getSegmentImportJobsRequest.getPageSize() != null && !getSegmentImportJobsRequest.getPageSize().equals(getPageSize())) {
            return false;
        }
        if ((getSegmentImportJobsRequest.getSegmentId() == null) ^ (getSegmentId() == null)) {
            return false;
        }
        if (getSegmentImportJobsRequest.getSegmentId() != null && !getSegmentImportJobsRequest.getSegmentId().equals(getSegmentId())) {
            return false;
        }
        if ((getSegmentImportJobsRequest.getToken() == null) ^ (getToken() == null)) {
            return false;
        }
        return getSegmentImportJobsRequest.getToken() == null || getSegmentImportJobsRequest.getToken().equals(getToken());
    }
}
