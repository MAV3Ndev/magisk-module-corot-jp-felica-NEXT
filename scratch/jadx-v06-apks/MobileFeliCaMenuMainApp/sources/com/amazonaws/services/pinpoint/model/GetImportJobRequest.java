package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetImportJobRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private String jobId;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public GetImportJobRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getJobId() {
        return this.jobId;
    }

    public void setJobId(String str) {
        this.jobId = str;
    }

    public GetImportJobRequest withJobId(String str) {
        this.jobId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getJobId() != null) {
            sb.append("JobId: " + getJobId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getJobId() != null ? getJobId().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetImportJobRequest)) {
            return false;
        }
        GetImportJobRequest getImportJobRequest = (GetImportJobRequest) obj;
        if ((getImportJobRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (getImportJobRequest.getApplicationId() != null && !getImportJobRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((getImportJobRequest.getJobId() == null) ^ (getJobId() == null)) {
            return false;
        }
        return getImportJobRequest.getJobId() == null || getImportJobRequest.getJobId().equals(getJobId());
    }
}
