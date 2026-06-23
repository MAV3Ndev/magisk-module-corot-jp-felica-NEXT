package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentExportJobsResult implements Serializable {
    private ExportJobsResponse exportJobsResponse;

    public ExportJobsResponse getExportJobsResponse() {
        return this.exportJobsResponse;
    }

    public void setExportJobsResponse(ExportJobsResponse exportJobsResponse) {
        this.exportJobsResponse = exportJobsResponse;
    }

    public GetSegmentExportJobsResult withExportJobsResponse(ExportJobsResponse exportJobsResponse) {
        this.exportJobsResponse = exportJobsResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
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
        if (obj == null || !(obj instanceof GetSegmentExportJobsResult)) {
            return false;
        }
        GetSegmentExportJobsResult getSegmentExportJobsResult = (GetSegmentExportJobsResult) obj;
        if ((getSegmentExportJobsResult.getExportJobsResponse() == null) ^ (getExportJobsResponse() == null)) {
            return false;
        }
        return getSegmentExportJobsResult.getExportJobsResponse() == null || getSegmentExportJobsResult.getExportJobsResponse().equals(getExportJobsResponse());
    }
}
