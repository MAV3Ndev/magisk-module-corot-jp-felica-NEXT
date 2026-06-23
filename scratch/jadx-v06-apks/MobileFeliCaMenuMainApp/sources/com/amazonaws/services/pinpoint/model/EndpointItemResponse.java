package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class EndpointItemResponse implements Serializable {
    private String message;
    private Integer statusCode;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public EndpointItemResponse withMessage(String str) {
        this.message = str;
        return this;
    }

    public Integer getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(Integer num) {
        this.statusCode = num;
    }

    public EndpointItemResponse withStatusCode(Integer num) {
        this.statusCode = num;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getMessage() != null) {
            sb.append("Message: " + getMessage() + ",");
        }
        if (getStatusCode() != null) {
            sb.append("StatusCode: " + getStatusCode());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getMessage() == null ? 0 : getMessage().hashCode()) + 31) * 31) + (getStatusCode() != null ? getStatusCode().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EndpointItemResponse)) {
            return false;
        }
        EndpointItemResponse endpointItemResponse = (EndpointItemResponse) obj;
        if ((endpointItemResponse.getMessage() == null) ^ (getMessage() == null)) {
            return false;
        }
        if (endpointItemResponse.getMessage() != null && !endpointItemResponse.getMessage().equals(getMessage())) {
            return false;
        }
        if ((endpointItemResponse.getStatusCode() == null) ^ (getStatusCode() == null)) {
            return false;
        }
        return endpointItemResponse.getStatusCode() == null || endpointItemResponse.getStatusCode().equals(getStatusCode());
    }
}
