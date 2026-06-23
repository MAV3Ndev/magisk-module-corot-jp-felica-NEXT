package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetExportJobsRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private String pageSize;
    private String token;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public GetExportJobsRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(String str) {
        this.pageSize = str;
    }

    public GetExportJobsRequest withPageSize(String str) {
        this.pageSize = str;
        return this;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public GetExportJobsRequest withToken(String str) {
        this.token = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getPageSize() != null) {
            sb.append("PageSize: " + getPageSize() + ",");
        }
        if (getToken() != null) {
            sb.append("Token: " + getToken());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getPageSize() == null ? 0 : getPageSize().hashCode())) * 31) + (getToken() != null ? getToken().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetExportJobsRequest)) {
            return false;
        }
        GetExportJobsRequest getExportJobsRequest = (GetExportJobsRequest) obj;
        if ((getExportJobsRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (getExportJobsRequest.getApplicationId() != null && !getExportJobsRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((getExportJobsRequest.getPageSize() == null) ^ (getPageSize() == null)) {
            return false;
        }
        if (getExportJobsRequest.getPageSize() != null && !getExportJobsRequest.getPageSize().equals(getPageSize())) {
            return false;
        }
        if ((getExportJobsRequest.getToken() == null) ^ (getToken() == null)) {
            return false;
        }
        return getExportJobsRequest.getToken() == null || getExportJobsRequest.getToken().equals(getToken());
    }
}
