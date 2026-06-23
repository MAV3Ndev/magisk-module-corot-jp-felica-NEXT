package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetImportJobsResult implements Serializable {
    private ImportJobsResponse importJobsResponse;

    public ImportJobsResponse getImportJobsResponse() {
        return this.importJobsResponse;
    }

    public void setImportJobsResponse(ImportJobsResponse importJobsResponse) {
        this.importJobsResponse = importJobsResponse;
    }

    public GetImportJobsResult withImportJobsResponse(ImportJobsResponse importJobsResponse) {
        this.importJobsResponse = importJobsResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getImportJobsResponse() != null) {
            sb.append("ImportJobsResponse: " + getImportJobsResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getImportJobsResponse() == null ? 0 : getImportJobsResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetImportJobsResult)) {
            return false;
        }
        GetImportJobsResult getImportJobsResult = (GetImportJobsResult) obj;
        if ((getImportJobsResult.getImportJobsResponse() == null) ^ (getImportJobsResponse() == null)) {
            return false;
        }
        return getImportJobsResult.getImportJobsResponse() == null || getImportJobsResult.getImportJobsResponse().equals(getImportJobsResponse());
    }
}
