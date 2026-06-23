package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class EndpointRequest implements Serializable {
    private String address;
    private Map<String, List<String>> attributes;
    private String channelType;
    private EndpointDemographic demographic;
    private String effectiveDate;
    private String endpointStatus;
    private EndpointLocation location;
    private Map<String, Double> metrics;
    private String optOut;
    private String requestId;
    private EndpointUser user;

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public EndpointRequest withAddress(String str) {
        this.address = str;
        return this;
    }

    public Map<String, List<String>> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, List<String>> map) {
        this.attributes = map;
    }

    public EndpointRequest withAttributes(Map<String, List<String>> map) {
        this.attributes = map;
        return this;
    }

    public EndpointRequest addAttributesEntry(String str, List<String> list) {
        if (this.attributes == null) {
            this.attributes = new HashMap();
        }
        if (this.attributes.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.attributes.put(str, list);
        return this;
    }

    public EndpointRequest clearAttributesEntries() {
        this.attributes = null;
        return this;
    }

    public String getChannelType() {
        return this.channelType;
    }

    public void setChannelType(String str) {
        this.channelType = str;
    }

    public EndpointRequest withChannelType(String str) {
        this.channelType = str;
        return this;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType.toString();
    }

    public EndpointRequest withChannelType(ChannelType channelType) {
        this.channelType = channelType.toString();
        return this;
    }

    public EndpointDemographic getDemographic() {
        return this.demographic;
    }

    public void setDemographic(EndpointDemographic endpointDemographic) {
        this.demographic = endpointDemographic;
    }

    public EndpointRequest withDemographic(EndpointDemographic endpointDemographic) {
        this.demographic = endpointDemographic;
        return this;
    }

    public String getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(String str) {
        this.effectiveDate = str;
    }

    public EndpointRequest withEffectiveDate(String str) {
        this.effectiveDate = str;
        return this;
    }

    public String getEndpointStatus() {
        return this.endpointStatus;
    }

    public void setEndpointStatus(String str) {
        this.endpointStatus = str;
    }

    public EndpointRequest withEndpointStatus(String str) {
        this.endpointStatus = str;
        return this;
    }

    public EndpointLocation getLocation() {
        return this.location;
    }

    public void setLocation(EndpointLocation endpointLocation) {
        this.location = endpointLocation;
    }

    public EndpointRequest withLocation(EndpointLocation endpointLocation) {
        this.location = endpointLocation;
        return this;
    }

    public Map<String, Double> getMetrics() {
        return this.metrics;
    }

    public void setMetrics(Map<String, Double> map) {
        this.metrics = map;
    }

    public EndpointRequest withMetrics(Map<String, Double> map) {
        this.metrics = map;
        return this;
    }

    public EndpointRequest addMetricsEntry(String str, Double d) {
        if (this.metrics == null) {
            this.metrics = new HashMap();
        }
        if (this.metrics.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.metrics.put(str, d);
        return this;
    }

    public EndpointRequest clearMetricsEntries() {
        this.metrics = null;
        return this;
    }

    public String getOptOut() {
        return this.optOut;
    }

    public void setOptOut(String str) {
        this.optOut = str;
    }

    public EndpointRequest withOptOut(String str) {
        this.optOut = str;
        return this;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String str) {
        this.requestId = str;
    }

    public EndpointRequest withRequestId(String str) {
        this.requestId = str;
        return this;
    }

    public EndpointUser getUser() {
        return this.user;
    }

    public void setUser(EndpointUser endpointUser) {
        this.user = endpointUser;
    }

    public EndpointRequest withUser(EndpointUser endpointUser) {
        this.user = endpointUser;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAddress() != null) {
            sb.append("Address: " + getAddress() + ",");
        }
        if (getAttributes() != null) {
            sb.append("Attributes: " + getAttributes() + ",");
        }
        if (getChannelType() != null) {
            sb.append("ChannelType: " + getChannelType() + ",");
        }
        if (getDemographic() != null) {
            sb.append("Demographic: " + getDemographic() + ",");
        }
        if (getEffectiveDate() != null) {
            sb.append("EffectiveDate: " + getEffectiveDate() + ",");
        }
        if (getEndpointStatus() != null) {
            sb.append("EndpointStatus: " + getEndpointStatus() + ",");
        }
        if (getLocation() != null) {
            sb.append("Location: " + getLocation() + ",");
        }
        if (getMetrics() != null) {
            sb.append("Metrics: " + getMetrics() + ",");
        }
        if (getOptOut() != null) {
            sb.append("OptOut: " + getOptOut() + ",");
        }
        if (getRequestId() != null) {
            sb.append("RequestId: " + getRequestId() + ",");
        }
        if (getUser() != null) {
            sb.append("User: " + getUser());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((((((((((getAddress() == null ? 0 : getAddress().hashCode()) + 31) * 31) + (getAttributes() == null ? 0 : getAttributes().hashCode())) * 31) + (getChannelType() == null ? 0 : getChannelType().hashCode())) * 31) + (getDemographic() == null ? 0 : getDemographic().hashCode())) * 31) + (getEffectiveDate() == null ? 0 : getEffectiveDate().hashCode())) * 31) + (getEndpointStatus() == null ? 0 : getEndpointStatus().hashCode())) * 31) + (getLocation() == null ? 0 : getLocation().hashCode())) * 31) + (getMetrics() == null ? 0 : getMetrics().hashCode())) * 31) + (getOptOut() == null ? 0 : getOptOut().hashCode())) * 31) + (getRequestId() == null ? 0 : getRequestId().hashCode())) * 31) + (getUser() != null ? getUser().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EndpointRequest)) {
            return false;
        }
        EndpointRequest endpointRequest = (EndpointRequest) obj;
        if ((endpointRequest.getAddress() == null) ^ (getAddress() == null)) {
            return false;
        }
        if (endpointRequest.getAddress() != null && !endpointRequest.getAddress().equals(getAddress())) {
            return false;
        }
        if ((endpointRequest.getAttributes() == null) ^ (getAttributes() == null)) {
            return false;
        }
        if (endpointRequest.getAttributes() != null && !endpointRequest.getAttributes().equals(getAttributes())) {
            return false;
        }
        if ((endpointRequest.getChannelType() == null) ^ (getChannelType() == null)) {
            return false;
        }
        if (endpointRequest.getChannelType() != null && !endpointRequest.getChannelType().equals(getChannelType())) {
            return false;
        }
        if ((endpointRequest.getDemographic() == null) ^ (getDemographic() == null)) {
            return false;
        }
        if (endpointRequest.getDemographic() != null && !endpointRequest.getDemographic().equals(getDemographic())) {
            return false;
        }
        if ((endpointRequest.getEffectiveDate() == null) ^ (getEffectiveDate() == null)) {
            return false;
        }
        if (endpointRequest.getEffectiveDate() != null && !endpointRequest.getEffectiveDate().equals(getEffectiveDate())) {
            return false;
        }
        if ((endpointRequest.getEndpointStatus() == null) ^ (getEndpointStatus() == null)) {
            return false;
        }
        if (endpointRequest.getEndpointStatus() != null && !endpointRequest.getEndpointStatus().equals(getEndpointStatus())) {
            return false;
        }
        if ((endpointRequest.getLocation() == null) ^ (getLocation() == null)) {
            return false;
        }
        if (endpointRequest.getLocation() != null && !endpointRequest.getLocation().equals(getLocation())) {
            return false;
        }
        if ((endpointRequest.getMetrics() == null) ^ (getMetrics() == null)) {
            return false;
        }
        if (endpointRequest.getMetrics() != null && !endpointRequest.getMetrics().equals(getMetrics())) {
            return false;
        }
        if ((endpointRequest.getOptOut() == null) ^ (getOptOut() == null)) {
            return false;
        }
        if (endpointRequest.getOptOut() != null && !endpointRequest.getOptOut().equals(getOptOut())) {
            return false;
        }
        if ((endpointRequest.getRequestId() == null) ^ (getRequestId() == null)) {
            return false;
        }
        if (endpointRequest.getRequestId() != null && !endpointRequest.getRequestId().equals(getRequestId())) {
            return false;
        }
        if ((endpointRequest.getUser() == null) ^ (getUser() == null)) {
            return false;
        }
        return endpointRequest.getUser() == null || endpointRequest.getUser().equals(getUser());
    }
}
