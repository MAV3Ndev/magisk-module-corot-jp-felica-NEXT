package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CreateImportJobResult implements Serializable {
    private ImportJobResponse importJobResponse;

    public ImportJobResponse getImportJobResponse() {
        return this.importJobResponse;
    }

    public void setImportJobResponse(ImportJobResponse importJobResponse) {
        this.importJobResponse = importJobResponse;
    }

    public CreateImportJobResult withImportJobResponse(ImportJobResponse importJobResponse) {
        this.importJobResponse = importJobResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getImportJobResponse() != null) {
            sb.append("ImportJobResponse: " + getImportJobResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getImportJobResponse() == null ? 0 : getImportJobResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CreateImportJobResult)) {
            return false;
        }
        CreateImportJobResult createImportJobResult = (CreateImportJobResult) obj;
        if ((createImportJobResult.getImportJobResponse() == null) ^ (getImportJobResponse() == null)) {
            return false;
        }
        return createImportJobResult.getImportJobResponse() == null || createImportJobResult.getImportJobResponse().equals(getImportJobResponse());
    }
}
