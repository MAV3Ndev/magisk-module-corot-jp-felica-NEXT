package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetImportJobsRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private String pageSize;
    private String token;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public GetImportJobsRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(String str) {
        this.pageSize = str;
    }

    public GetImportJobsRequest withPageSize(String str) {
        this.pageSize = str;
        return this;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public GetImportJobsRequest withToken(String str) {
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
        if (obj == null || !(obj instanceof GetImportJobsRequest)) {
            return false;
        }
        GetImportJobsRequest getImportJobsRequest = (GetImportJobsRequest) obj;
        if ((getImportJobsRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (getImportJobsRequest.getApplicationId() != null && !getImportJobsRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((getImportJobsRequest.getPageSize() == null) ^ (getPageSize() == null)) {
            return false;
        }
        if (getImportJobsRequest.getPageSize() != null && !getImportJobsRequest.getPageSize().equals(getPageSize())) {
            return false;
        }
        if ((getImportJobsRequest.getToken() == null) ^ (getToken() == null)) {
            return false;
        }
        return getImportJobsRequest.getToken() == null || getImportJobsRequest.getToken().equals(getToken());
    }
}
