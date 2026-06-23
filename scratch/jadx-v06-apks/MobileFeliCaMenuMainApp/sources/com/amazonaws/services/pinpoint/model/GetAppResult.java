package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetAppResult implements Serializable {
    private ApplicationResponse applicationResponse;

    public ApplicationResponse getApplicationResponse() {
        return this.applicationResponse;
    }

    public void setApplicationResponse(ApplicationResponse applicationResponse) {
        this.applicationResponse = applicationResponse;
    }

    public GetAppResult withApplicationResponse(ApplicationResponse applicationResponse) {
        this.applicationResponse = applicationResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationResponse() != null) {
            sb.append("ApplicationResponse: " + getApplicationResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getApplicationResponse() == null ? 0 : getApplicationResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetAppResult)) {
            return false;
        }
        GetAppResult getAppResult = (GetAppResult) obj;
        if ((getAppResult.getApplicationResponse() == null) ^ (getApplicationResponse() == null)) {
            return false;
        }
        return getAppResult.getApplicationResponse() == null || getAppResult.getApplicationResponse().equals(getApplicationResponse());
    }
}
