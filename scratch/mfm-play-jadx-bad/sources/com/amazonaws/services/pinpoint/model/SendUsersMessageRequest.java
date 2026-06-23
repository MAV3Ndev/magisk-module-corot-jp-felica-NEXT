package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SendUsersMessageRequest implements Serializable {
    private Map<String, String> context;
    private DirectMessageConfiguration messageConfiguration;
    private String traceId;
    private Map<String, EndpointSendConfiguration> users;

    public Map<String, String> getContext() {
        return this.context;
    }

    public void setContext(Map<String, String> map) {
        this.context = map;
    }

    public SendUsersMessageRequest withContext(Map<String, String> map) {
        this.context = map;
        return this;
    }

    public SendUsersMessageRequest addContextEntry(String str, String str2) {
        if (this.context == null) {
            this.context = new HashMap();
        }
        if (this.context.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.context.put(str, str2);
        return this;
    }

    public SendUsersMessageRequest clearContextEntries() {
        this.context = null;
        return this;
    }

    public DirectMessageConfiguration getMessageConfiguration() {
        return this.messageConfiguration;
    }

    public void setMessageConfiguration(DirectMessageConfiguration directMessageConfiguration) {
        this.messageConfiguration = directMessageConfiguration;
    }

    public SendUsersMessageRequest withMessageConfiguration(DirectMessageConfiguration directMessageConfiguration) {
        this.messageConfiguration = directMessageConfiguration;
        return this;
    }

    public String getTraceId() {
        return this.traceId;
    }

    public void setTraceId(String str) {
        this.traceId = str;
    }

    public SendUsersMessageRequest withTraceId(String str) {
        this.traceId = str;
        return this;
    }

    public Map<String, EndpointSendConfiguration> getUsers() {
        return this.users;
    }

    public void setUsers(Map<String, EndpointSendConfiguration> map) {
        this.users = map;
    }

    public SendUsersMessageRequest withUsers(Map<String, EndpointSendConfiguration> map) {
        this.users = map;
        return this;
    }

    public SendUsersMessageRequest addUsersEntry(String str, EndpointSendConfiguration endpointSendConfiguration) {
        if (this.users == null) {
            this.users = new HashMap();
        }
        if (this.users.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.users.put(str, endpointSendConfiguration);
        return this;
    }

    public SendUsersMessageRequest clearUsersEntries() {
        this.users = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getContext() != null) {
            sb.append("Context: " + getContext() + ",");
        }
        if (getMessageConfiguration() != null) {
            sb.append("MessageConfiguration: " + getMessageConfiguration() + ",");
        }
        if (getTraceId() != null) {
            sb.append("TraceId: " + getTraceId() + ",");
        }
        if (getUsers() != null) {
            sb.append("Users: " + getUsers());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((getContext() == null ? 0 : getContext().hashCode()) + 31) * 31) + (getMessageConfiguration() == null ? 0 : getMessageConfiguration().hashCode())) * 31) + (getTraceId() == null ? 0 : getTraceId().hashCode())) * 31) + (getUsers() != null ? getUsers().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SendUsersMessageRequest)) {
            return false;
        }
        SendUsersMessageRequest sendUsersMessageRequest = (SendUsersMessageRequest) obj;
        if ((sendUsersMessageRequest.getContext() == null) ^ (getContext() == null)) {
            return false;
        }
        if (sendUsersMessageRequest.getContext() != null && !sendUsersMessageRequest.getContext().equals(getContext())) {
            return false;
        }
        if ((sendUsersMessageRequest.getMessageConfiguration() == null) ^ (getMessageConfiguration() == null)) {
            return false;
        }
        if (sendUsersMessageRequest.getMessageConfiguration() != null && !sendUsersMessageRequest.getMessageConfiguration().equals(getMessageConfiguration())) {
            return false;
        }
        if ((sendUsersMessageRequest.getTraceId() == null) ^ (getTraceId() == null)) {
            return false;
        }
        if (sendUsersMessageRequest.getTraceId() != null && !sendUsersMessageRequest.getTraceId().equals(getTraceId())) {
            return false;
        }
        if ((sendUsersMessageRequest.getUsers() == null) ^ (getUsers() == null)) {
            return false;
        }
        return sendUsersMessageRequest.getUsers() == null || sendUsersMessageRequest.getUsers().equals(getUsers());
    }
}
