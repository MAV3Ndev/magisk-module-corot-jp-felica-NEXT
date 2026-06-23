package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DeleteEndpointResult implements Serializable {
    private EndpointResponse endpointResponse;

    public EndpointResponse getEndpointResponse() {
        return this.endpointResponse;
    }

    public void setEndpointResponse(EndpointResponse endpointResponse) {
        this.endpointResponse = endpointResponse;
    }

    public DeleteEndpointResult withEndpointResponse(EndpointResponse endpointResponse) {
        this.endpointResponse = endpointResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
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
        if (obj == null || !(obj instanceof DeleteEndpointResult)) {
            return false;
        }
        DeleteEndpointResult deleteEndpointResult = (DeleteEndpointResult) obj;
        if ((deleteEndpointResult.getEndpointResponse() == null) ^ (getEndpointResponse() == null)) {
            return false;
        }
        return deleteEndpointResult.getEndpointResponse() == null || deleteEndpointResult.getEndpointResponse().equals(getEndpointResponse());
    }
}
