package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CreateImportJobRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private ImportJobRequest importJobRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public CreateImportJobRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public ImportJobRequest getImportJobRequest() {
        return this.importJobRequest;
    }

    public void setImportJobRequest(ImportJobRequest importJobRequest) {
        this.importJobRequest = importJobRequest;
    }

    public CreateImportJobRequest withImportJobRequest(ImportJobRequest importJobRequest) {
        this.importJobRequest = importJobRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getImportJobRequest() != null) {
            sb.append("ImportJobRequest: " + getImportJobRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getImportJobRequest() != null ? getImportJobRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CreateImportJobRequest)) {
            return false;
        }
        CreateImportJobRequest createImportJobRequest = (CreateImportJobRequest) obj;
        if ((createImportJobRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (createImportJobRequest.getApplicationId() != null && !createImportJobRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((createImportJobRequest.getImportJobRequest() == null) ^ (getImportJobRequest() == null)) {
            return false;
        }
        return createImportJobRequest.getImportJobRequest() == null || createImportJobRequest.getImportJobRequest().equals(getImportJobRequest());
    }
}
