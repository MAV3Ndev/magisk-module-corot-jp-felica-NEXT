package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetExportJobsResult implements Serializable {
    private ExportJobsResponse exportJobsResponse;

    public ExportJobsResponse getExportJobsResponse() {
        return this.exportJobsResponse;
    }

    public void setExportJobsResponse(ExportJobsResponse exportJobsResponse) {
        this.exportJobsResponse = exportJobsResponse;
    }

    public GetExportJobsResult withExportJobsResponse(ExportJobsResponse exportJobsResponse) {
        this.exportJobsResponse = exportJobsResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getExportJobsResponse() != null) {
            sb.append("ExportJobsResponse: " + getExportJobsResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getExportJobsResponse() == null ? 0 : getExportJobsResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetExportJobsResult)) {
            return false;
        }
        GetExportJobsResult getExportJobsResult = (GetExportJobsResult) obj;
        if ((getExportJobsResult.getExportJobsResponse() == null) ^ (getExportJobsResponse() == null)) {
            return false;
        }
        return getExportJobsResult.getExportJobsResponse() == null || getExportJobsResult.getExportJobsResponse().equals(getExportJobsResponse());
    }
}
