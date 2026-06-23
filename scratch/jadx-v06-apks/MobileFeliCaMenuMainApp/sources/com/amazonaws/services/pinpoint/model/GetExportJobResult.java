package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetExportJobResult implements Serializable {
    private ExportJobResponse exportJobResponse;

    public ExportJobResponse getExportJobResponse() {
        return this.exportJobResponse;
    }

    public void setExportJobResponse(ExportJobResponse exportJobResponse) {
        this.exportJobResponse = exportJobResponse;
    }

    public GetExportJobResult withExportJobResponse(ExportJobResponse exportJobResponse) {
        this.exportJobResponse = exportJobResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getExportJobResponse() != null) {
            sb.append("ExportJobResponse: " + getExportJobResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getExportJobResponse() == null ? 0 : getExportJobResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetExportJobResult)) {
            return false;
        }
        GetExportJobResult getExportJobResult = (GetExportJobResult) obj;
        if ((getExportJobResult.getExportJobResponse() == null) ^ (getExportJobResponse() == null)) {
            return false;
        }
        return getExportJobResult.getExportJobResponse() == null || getExportJobResult.getExportJobResponse().equals(getExportJobResponse());
    }
}
