package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MessageResponse implements Serializable {
    private String applicationId;
    private Map<String, EndpointMessageResult> endpointResult;
    private String requestId;
    private Map<String, MessageResult> result;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public MessageResponse withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public Map<String, EndpointMessageResult> getEndpointResult() {
        return this.endpointResult;
    }

    public void setEndpointResult(Map<String, EndpointMessageResult> map) {
        this.endpointResult = map;
    }

    public MessageResponse withEndpointResult(Map<String, EndpointMessageResult> map) {
        this.endpointResult = map;
        return this;
    }

    public MessageResponse addEndpointResultEntry(String str, EndpointMessageResult endpointMessageResult) {
        if (this.endpointResult == null) {
            this.endpointResult = new HashMap();
        }
        if (this.endpointResult.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.endpointResult.put(str, endpointMessageResult);
        return this;
    }

    public MessageResponse clearEndpointResultEntries() {
        this.endpointResult = null;
        return this;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String str) {
        this.requestId = str;
    }

    public MessageResponse withRequestId(String str) {
        this.requestId = str;
        return this;
    }

    public Map<String, MessageResult> getResult() {
        return this.result;
    }

    public void setResult(Map<String, MessageResult> map) {
        this.result = map;
    }

    public MessageResponse withResult(Map<String, MessageResult> map) {
        this.result = map;
        return this;
    }

    public MessageResponse addResultEntry(String str, MessageResult messageResult) {
        if (this.result == null) {
            this.result = new HashMap();
        }
        if (this.result.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.result.put(str, messageResult);
        return this;
    }

    public MessageResponse clearResultEntries() {
        this.result = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getEndpointResult() != null) {
            sb.append("EndpointResult: " + getEndpointResult() + ",");
        }
        if (getRequestId() != null) {
            sb.append("RequestId: " + getRequestId() + ",");
        }
        if (getResult() != null) {
            sb.append("Result: " + getResult());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getEndpointResult() == null ? 0 : getEndpointResult().hashCode())) * 31) + (getRequestId() == null ? 0 : getRequestId().hashCode())) * 31) + (getResult() != null ? getResult().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MessageResponse)) {
            return false;
        }
        MessageResponse messageResponse = (MessageResponse) obj;
        if ((messageResponse.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (messageResponse.getApplicationId() != null && !messageResponse.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((messageResponse.getEndpointResult() == null) ^ (getEndpointResult() == null)) {
            return false;
        }
        if (messageResponse.getEndpointResult() != null && !messageResponse.getEndpointResult().equals(getEndpointResult())) {
            return false;
        }
        if ((messageResponse.getRequestId() == null) ^ (getRequestId() == null)) {
            return false;
        }
        if (messageResponse.getRequestId() != null && !messageResponse.getRequestId().equals(getRequestId())) {
            return false;
        }
        if ((messageResponse.getResult() == null) ^ (getResult() == null)) {
            return false;
        }
        return messageResponse.getResult() == null || messageResponse.getResult().equals(getResult());
    }
}
