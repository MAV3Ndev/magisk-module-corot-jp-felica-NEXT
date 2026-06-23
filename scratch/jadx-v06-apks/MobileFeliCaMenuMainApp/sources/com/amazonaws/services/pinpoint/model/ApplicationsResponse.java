package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ApplicationsResponse implements Serializable {
    private List<ApplicationResponse> item;
    private String nextToken;

    public List<ApplicationResponse> getItem() {
        return this.item;
    }

    public void setItem(Collection<ApplicationResponse> collection) {
        if (collection == null) {
            this.item = null;
        } else {
            this.item = new ArrayList(collection);
        }
    }

    public ApplicationsResponse withItem(ApplicationResponse... applicationResponseArr) {
        if (getItem() == null) {
            this.item = new ArrayList(applicationResponseArr.length);
        }
        for (ApplicationResponse applicationResponse : applicationResponseArr) {
            this.item.add(applicationResponse);
        }
        return this;
    }

    public ApplicationsResponse withItem(Collection<ApplicationResponse> collection) {
        setItem(collection);
        return this;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String str) {
        this.nextToken = str;
    }

    public ApplicationsResponse withNextToken(String str) {
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
        if (obj == null || !(obj instanceof ApplicationsResponse)) {
            return false;
        }
        ApplicationsResponse applicationsResponse = (ApplicationsResponse) obj;
        if ((applicationsResponse.getItem() == null) ^ (getItem() == null)) {
            return false;
        }
        if (applicationsResponse.getItem() != null && !applicationsResponse.getItem().equals(getItem())) {
            return false;
        }
        if ((applicationsResponse.getNextToken() == null) ^ (getNextToken() == null)) {
            return false;
        }
        return applicationsResponse.getNextToken() == null || applicationsResponse.getNextToken().equals(getNextToken());
    }
}
