package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ExportJobsResponse implements Serializable {
    private List<ExportJobResponse> item;
    private String nextToken;

    public List<ExportJobResponse> getItem() {
        return this.item;
    }

    public void setItem(Collection<ExportJobResponse> collection) {
        if (collection == null) {
            this.item = null;
        } else {
            this.item = new ArrayList(collection);
        }
    }

    public ExportJobsResponse withItem(ExportJobResponse... exportJobResponseArr) {
        if (getItem() == null) {
            this.item = new ArrayList(exportJobResponseArr.length);
        }
        for (ExportJobResponse exportJobResponse : exportJobResponseArr) {
            this.item.add(exportJobResponse);
        }
        return this;
    }

    public ExportJobsResponse withItem(Collection<ExportJobResponse> collection) {
        setItem(collection);
        return this;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String str) {
        this.nextToken = str;
    }

    public ExportJobsResponse withNextToken(String str) {
        this.nextToken = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getItem() != null) {
            sb.append("Item: " + getItem() + ",");
        }
        if (getNextToken() != null) {
            sb.append("NextToken: " + getNextToken());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getItem() == null ? 0 : getItem().hashCode()) + 31) * 31) + (getNextToken() != null ? getNextToken().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ExportJobsResponse)) {
            return false;
        }
        ExportJobsResponse exportJobsResponse = (ExportJobsResponse) obj;
        if ((exportJobsResponse.getItem() == null) ^ (getItem() == null)) {
            return false;
        }
        if (exportJobsResponse.getItem() != null && !exportJobsResponse.getItem().equals(getItem())) {
            return false;
        }
        if ((exportJobsResponse.getNextToken() == null) ^ (getNextToken() == null)) {
            return false;
        }
        return exportJobsResponse.getNextToken() == null || exportJobsResponse.getNextToken().equals(getNextToken());
    }
}
