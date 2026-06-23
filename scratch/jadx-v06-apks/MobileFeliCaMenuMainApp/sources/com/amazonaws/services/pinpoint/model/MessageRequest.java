package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MessageRequest implements Serializable {
    private Map<String, AddressConfiguration> addresses;
    private Map<String, String> context;
    private Map<String, EndpointSendConfiguration> endpoints;
    private DirectMessageConfiguration messageConfiguration;
    private String traceId;

    public Map<String, AddressConfiguration> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Map<String, AddressConfiguration> map) {
        this.addresses = map;
    }

    public MessageRequest withAddresses(Map<String, AddressConfiguration> map) {
        this.addresses = map;
        return this;
    }

    public MessageRequest addAddressesEntry(String str, AddressConfiguration addressConfiguration) {
        if (this.addresses == null) {
            this.addresses = new HashMap();
        }
        if (this.addresses.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.addresses.put(str, addressConfiguration);
        return this;
    }

    public MessageRequest clearAddressesEntries() {
        this.addresses = null;
        return this;
    }

    public Map<String, String> getContext() {
        return this.context;
    }

    public void setContext(Map<String, String> map) {
        this.context = map;
    }

    public MessageRequest withContext(Map<String, String> map) {
        this.context = map;
        return this;
    }

    public MessageRequest addContextEntry(String str, String str2) {
        if (this.context == null) {
            this.context = new HashMap();
        }
        if (this.context.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.context.put(str, str2);
        return this;
    }

    public MessageRequest clearContextEntries() {
        this.context = null;
        return this;
    }

    public Map<String, EndpointSendConfiguration> getEndpoints() {
        return this.endpoints;
    }

    public void setEndpoints(Map<String, EndpointSendConfiguration> map) {
        this.endpoints = map;
    }

    public MessageRequest withEndpoints(Map<String, EndpointSendConfiguration> map) {
        this.endpoints = map;
        return this;
    }

    public MessageRequest addEndpointsEntry(String str, EndpointSendConfiguration endpointSendConfiguration) {
        if (this.endpoints == null) {
            this.endpoints = new HashMap();
        }
        if (this.endpoints.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.endpoints.put(str, endpointSendConfiguration);
        return this;
    }

    public MessageRequest clearEndpointsEntries() {
        this.endpoints = null;
        return this;
    }

    public DirectMessageConfiguration getMessageConfiguration() {
        return this.messageConfiguration;
    }

    public void setMessageConfiguration(DirectMessageConfiguration directMessageConfiguration) {
        this.messageConfiguration = directMessageConfiguration;
    }

    public MessageRequest withMessageConfiguration(DirectMessageConfiguration directMessageConfiguration) {
        this.messageConfiguration = directMessageConfiguration;
        return this;
    }

    public String getTraceId() {
        return this.traceId;
    }

    public void setTraceId(String str) {
        this.traceId = str;
    }

    public MessageRequest withTraceId(String str) {
        this.traceId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAddresses() != null) {
            sb.append("Addresses: " + getAddresses() + ",");
        }
        if (getContext() != null) {
            sb.append("Context: " + getContext() + ",");
        }
        if (getEndpoints() != null) {
            sb.append("Endpoints: " + getEndpoints() + ",");
        }
        if (getMessageConfiguration() != null) {
            sb.append("MessageConfiguration: " + getMessageConfiguration() + ",");
        }
        if (getTraceId() != null) {
            sb.append("TraceId: " + getTraceId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((getAddresses() == null ? 0 : getAddresses().hashCode()) + 31) * 31) + (getContext() == null ? 0 : getContext().hashCode())) * 31) + (getEndpoints() == null ? 0 : getEndpoints().hashCode())) * 31) + (getMessageConfiguration() == null ? 0 : getMessageConfiguration().hashCode())) * 31) + (getTraceId() != null ? getTraceId().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MessageRequest)) {
            return false;
        }
        MessageRequest messageRequest = (MessageRequest) obj;
        if ((messageRequest.getAddresses() == null) ^ (getAddresses() == null)) {
            return false;
        }
        if (messageRequest.getAddresses() != null && !messageRequest.getAddresses().equals(getAddresses())) {
            return false;
        }
        if ((messageRequest.getContext() == null) ^ (getContext() == null)) {
            return false;
        }
        if (messageRequest.getContext() != null && !messageRequest.getContext().equals(getContext())) {
            return false;
        }
        if ((messageRequest.getEndpoints() == null) ^ (getEndpoints() == null)) {
            return false;
        }
        if (messageRequest.getEndpoints() != null && !messageRequest.getEndpoints().equals(getEndpoints())) {
            return false;
        }
        if ((messageRequest.getMessageConfiguration() == null) ^ (getMessageConfiguration() == null)) {
            return false;
        }
        if (messageRequest.getMessageConfiguration() != null && !messageRequest.getMessageConfiguration().equals(getMessageConfiguration())) {
            return false;
        }
        if ((messageRequest.getTraceId() == null) ^ (getTraceId() == null)) {
            return false;
        }
        return messageRequest.getTraceId() == null || messageRequest.getTraceId().equals(getTraceId());
    }
}
