package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ImportJobsResponse implements Serializable {
    private List<ImportJobResponse> item;
    private String nextToken;

    public List<ImportJobResponse> getItem() {
        return this.item;
    }

    public void setItem(Collection<ImportJobResponse> collection) {
        if (collection == null) {
            this.item = null;
        } else {
            this.item = new ArrayList(collection);
        }
    }

    public ImportJobsResponse withItem(ImportJobResponse... importJobResponseArr) {
        if (getItem() == null) {
            this.item = new ArrayList(importJobResponseArr.length);
        }
        for (ImportJobResponse importJobResponse : importJobResponseArr) {
            this.item.add(importJobResponse);
        }
        return this;
    }

    public ImportJobsResponse withItem(Collection<ImportJobResponse> collection) {
        setItem(collection);
        return this;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String str) {
        this.nextToken = str;
    }

    public ImportJobsResponse withNextToken(String str) {
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
        if (obj == null || !(obj instanceof ImportJobsResponse)) {
            return false;
        }
        ImportJobsResponse importJobsResponse = (ImportJobsResponse) obj;
        if ((importJobsResponse.getItem() == null) ^ (getItem() == null)) {
            return false;
        }
        if (importJobsResponse.getItem() != null && !importJobsResponse.getItem().equals(getItem())) {
            return false;
        }
        if ((importJobsResponse.getNextToken() == null) ^ (getNextToken() == null)) {
            return false;
        }
        return importJobsResponse.getNextToken() == null || importJobsResponse.getNextToken().equals(getNextToken());
    }
}
