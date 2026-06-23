package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetAppsResult implements Serializable {
    private ApplicationsResponse applicationsResponse;

    public ApplicationsResponse getApplicationsResponse() {
        return this.applicationsResponse;
    }

    public void setApplicationsResponse(ApplicationsResponse applicationsResponse) {
        this.applicationsResponse = applicationsResponse;
    }

    public GetAppsResult withApplicationsResponse(ApplicationsResponse applicationsResponse) {
        this.applicationsResponse = applicationsResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationsResponse() != null) {
            sb.append("ApplicationsResponse: " + getApplicationsResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getApplicationsResponse() == null ? 0 : getApplicationsResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetAppsResult)) {
            return false;
        }
        GetAppsResult getAppsResult = (GetAppsResult) obj;
        if ((getAppsResult.getApplicationsResponse() == null) ^ (getApplicationsResponse() == null)) {
            return false;
        }
        return getAppsResult.getApplicationsResponse() == null || getAppsResult.getApplicationsResponse().equals(getApplicationsResponse());
    }
}
