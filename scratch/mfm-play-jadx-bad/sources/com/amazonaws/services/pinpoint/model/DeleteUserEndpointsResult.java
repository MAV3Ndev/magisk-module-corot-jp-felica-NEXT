package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DeleteUserEndpointsResult implements Serializable {
    private EndpointsResponse endpointsResponse;

    public EndpointsResponse getEndpointsResponse() {
        return this.endpointsResponse;
    }

    public void setEndpointsResponse(EndpointsResponse endpointsResponse) {
        this.endpointsResponse = endpointsResponse;
    }

    public DeleteUserEndpointsResult withEndpointsResponse(EndpointsResponse endpointsResponse) {
        this.endpointsResponse = endpointsResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getEndpointsResponse() != null) {
            sb.append("EndpointsResponse: " + getEndpointsResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getEndpointsResponse() == null ? 0 : getEndpointsResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DeleteUserEndpointsResult)) {
            return false;
        }
        DeleteUserEndpointsResult deleteUserEndpointsResult = (DeleteUserEndpointsResult) obj;
        if ((deleteUserEndpointsResult.getEndpointsResponse() == null) ^ (getEndpointsResponse() == null)) {
            return false;
        }
        return deleteUserEndpointsResult.getEndpointsResponse() == null || deleteUserEndpointsResult.getEndpointsResponse().equals(getEndpointsResponse());
    }
}
