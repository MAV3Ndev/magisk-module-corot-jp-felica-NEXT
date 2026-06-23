package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CreateExportJobRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private ExportJobRequest exportJobRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public CreateExportJobRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public ExportJobRequest getExportJobRequest() {
        return this.exportJobRequest;
    }

    public void setExportJobRequest(ExportJobRequest exportJobRequest) {
        this.exportJobRequest = exportJobRequest;
    }

    public CreateExportJobRequest withExportJobRequest(ExportJobRequest exportJobRequest) {
        this.exportJobRequest = exportJobRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getExportJobRequest() != null) {
            sb.append("ExportJobRequest: " + getExportJobRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getExportJobRequest() != null ? getExportJobRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CreateExportJobRequest)) {
            return false;
        }
        CreateExportJobRequest createExportJobRequest = (CreateExportJobRequest) obj;
        if ((createExportJobRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (createExportJobRequest.getApplicationId() != null && !createExportJobRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((createExportJobRequest.getExportJobRequest() == null) ^ (getExportJobRequest() == null)) {
            return false;
        }
        return createExportJobRequest.getExportJobRequest() == null || createExportJobRequest.getExportJobRequest().equals(getExportJobRequest());
    }
}
