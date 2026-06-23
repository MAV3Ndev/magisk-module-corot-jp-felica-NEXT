package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetAppsRequest extends AmazonWebServiceRequest implements Serializable {
    private String pageSize;
    private String token;

    public String getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(String str) {
        this.pageSize = str;
    }

    public GetAppsRequest withPageSize(String str) {
        this.pageSize = str;
        return this;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public GetAppsRequest withToken(String str) {
        this.token = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getPageSize() != null) {
            sb.append("PageSize: " + getPageSize() + ",");
        }
        if (getToken() != null) {
            sb.append("Token: " + getToken());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getPageSize() == null ? 0 : getPageSize().hashCode()) + 31) * 31) + (getToken() != null ? getToken().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetAppsRequest)) {
            return false;
        }
        GetAppsRequest getAppsRequest = (GetAppsRequest) obj;
        if ((getAppsRequest.getPageSize() == null) ^ (getPageSize() == null)) {
            return false;
        }
        if (getAppsRequest.getPageSize() != null && !getAppsRequest.getPageSize().equals(getPageSize())) {
            return false;
        }
        if ((getAppsRequest.getToken() == null) ^ (getToken() == null)) {
            return false;
        }
        return getAppsRequest.getToken() == null || getAppsRequest.getToken().equals(getToken());
    }
}
