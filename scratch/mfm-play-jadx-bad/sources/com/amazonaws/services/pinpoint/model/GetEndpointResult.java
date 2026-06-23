package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetEndpointResult implements Serializable {
    private EndpointResponse endpointResponse;

    public EndpointResponse getEndpointResponse() {
        return this.endpointResponse;
    }

    public void setEndpointResponse(EndpointResponse endpointResponse) {
        this.endpointResponse = endpointResponse;
    }

    public GetEndpointResult withEndpointResponse(EndpointResponse endpointResponse) {
        this.endpointResponse = endpointResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getEndpointResponse() != null) {
            sb.append("EndpointResponse: " + getEndpointResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getEndpointResponse() == null ? 0 : getEndpointResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetEndpointResult)) {
            return false;
        }
        GetEndpointResult getEndpointResult = (GetEndpointResult) obj;
        if ((getEndpointResult.getEndpointResponse() == null) ^ (getEndpointResponse() == null)) {
            return false;
        }
        return getEndpointResult.getEndpointResponse() == null || getEndpointResult.getEndpointResponse().equals(getEndpointResponse());
    }
}
