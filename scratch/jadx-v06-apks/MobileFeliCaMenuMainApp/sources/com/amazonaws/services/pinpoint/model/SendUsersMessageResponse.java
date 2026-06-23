package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SendUsersMessageResponse implements Serializable {
    private String applicationId;
    private String requestId;
    private Map<String, Map<String, EndpointMessageResult>> result;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public SendUsersMessageResponse withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String str) {
        this.requestId = str;
    }

    public SendUsersMessageResponse withRequestId(String str) {
        this.requestId = str;
        return this;
    }

    public Map<String, Map<String, EndpointMessageResult>> getResult() {
        return this.result;
    }

    public void setResult(Map<String, Map<String, EndpointMessageResult>> map) {
        this.result = map;
    }

    public SendUsersMessageResponse withResult(Map<String, Map<String, EndpointMessageResult>> map) {
        this.result = map;
        return this;
    }

    public SendUsersMessageResponse addResultEntry(String str, Map<String, EndpointMessageResult> map) {
        if (this.result == null) {
            this.result = new HashMap();
        }
        if (this.result.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.result.put(str, map);
        return this;
    }

    public SendUsersMessageResponse clearResultEntries() {
        this.result = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
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
        return (((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getRequestId() == null ? 0 : getRequestId().hashCode())) * 31) + (getResult() != null ? getResult().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SendUsersMessageResponse)) {
            return false;
        }
        SendUsersMessageResponse sendUsersMessageResponse = (SendUsersMessageResponse) obj;
        if ((sendUsersMessageResponse.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (sendUsersMessageResponse.getApplicationId() != null && !sendUsersMessageResponse.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((sendUsersMessageResponse.getRequestId() == null) ^ (getRequestId() == null)) {
            return false;
        }
        if (sendUsersMessageResponse.getRequestId() != null && !sendUsersMessageResponse.getRequestId().equals(getRequestId())) {
            return false;
        }
        if ((sendUsersMessageResponse.getResult() == null) ^ (getResult() == null)) {
            return false;
        }
        return sendUsersMessageResponse.getResult() == null || sendUsersMessageResponse.getResult().equals(getResult());
    }
}
