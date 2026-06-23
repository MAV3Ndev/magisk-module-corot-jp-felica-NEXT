package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class EndpointBatchItem implements Serializable {
    private String address;
    private Map<String, List<String>> attributes;
    private String channelType;
    private EndpointDemographic demographic;
    private String effectiveDate;
    private String endpointStatus;
    private String id;
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

    public EndpointBatchItem withAddress(String str) {
        this.address = str;
        return this;
    }

    public Map<String, List<String>> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, List<String>> map) {
        this.attributes = map;
    }

    public EndpointBatchItem withAttributes(Map<String, List<String>> map) {
        this.attributes = map;
        return this;
    }

    public EndpointBatchItem addAttributesEntry(String str, List<String> list) {
        if (this.attributes == null) {
            this.attributes = new HashMap();
        }
        if (this.attributes.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.attributes.put(str, list);
        return this;
    }

    public EndpointBatchItem clearAttributesEntries() {
        this.attributes = null;
        return this;
    }

    public String getChannelType() {
        return this.channelType;
    }

    public void setChannelType(String str) {
        this.channelType = str;
    }

    public EndpointBatchItem withChannelType(String str) {
        this.channelType = str;
        return this;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType.toString();
    }

    public EndpointBatchItem withChannelType(ChannelType channelType) {
        this.channelType = channelType.toString();
        return this;
    }

    public EndpointDemographic getDemographic() {
        return this.demographic;
    }

    public void setDemographic(EndpointDemographic endpointDemographic) {
        this.demographic = endpointDemographic;
    }

    public EndpointBatchItem withDemographic(EndpointDemographic endpointDemographic) {
        this.demographic = endpointDemographic;
        return this;
    }

    public String getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(String str) {
        this.effectiveDate = str;
    }

    public EndpointBatchItem withEffectiveDate(String str) {
        this.effectiveDate = str;
        return this;
    }

    public String getEndpointStatus() {
        return this.endpointStatus;
    }

    public void setEndpointStatus(String str) {
        this.endpointStatus = str;
    }

    public EndpointBatchItem withEndpointStatus(String str) {
        this.endpointStatus = str;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public EndpointBatchItem withId(String str) {
        this.id = str;
        return this;
    }

    public EndpointLocation getLocation() {
        return this.location;
    }

    public void setLocation(EndpointLocation endpointLocation) {
        this.location = endpointLocation;
    }

    public EndpointBatchItem withLocation(EndpointLocation endpointLocation) {
        this.location = endpointLocation;
        return this;
    }

    public Map<String, Double> getMetrics() {
        return this.metrics;
    }

    public void setMetrics(Map<String, Double> map) {
        this.metrics = map;
    }

    public EndpointBatchItem withMetrics(Map<String, Double> map) {
        this.metrics = map;
        return this;
    }

    public EndpointBatchItem addMetricsEntry(String str, Double d) {
        if (this.metrics == null) {
            this.metrics = new HashMap();
        }
        if (this.metrics.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.metrics.put(str, d);
        return this;
    }

    public EndpointBatchItem clearMetricsEntries() {
        this.metrics = null;
        return this;
    }

    public String getOptOut() {
        return this.optOut;
    }

    public void setOptOut(String str) {
        this.optOut = str;
    }

    public EndpointBatchItem withOptOut(String str) {
        this.optOut = str;
        return this;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String str) {
        this.requestId = str;
    }

    public EndpointBatchItem withRequestId(String str) {
        this.requestId = str;
        return this;
    }

    public EndpointUser getUser() {
        return this.user;
    }

    public void setUser(EndpointUser endpointUser) {
        this.user = endpointUser;
    }

    public EndpointBatchItem withUser(EndpointUser endpointUser) {
        this.user = endpointUser;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
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
        if (getId() != null) {
            sb.append("Id: " + getId() + ",");
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
        return (((((((((((((((((((((((getAddress() == null ? 0 : getAddress().hashCode()) + 31) * 31) + (getAttributes() == null ? 0 : getAttributes().hashCode())) * 31) + (getChannelType() == null ? 0 : getChannelType().hashCode())) * 31) + (getDemographic() == null ? 0 : getDemographic().hashCode())) * 31) + (getEffectiveDate() == null ? 0 : getEffectiveDate().hashCode())) * 31) + (getEndpointStatus() == null ? 0 : getEndpointStatus().hashCode())) * 31) + (getId() == null ? 0 : getId().hashCode())) * 31) + (getLocation() == null ? 0 : getLocation().hashCode())) * 31) + (getMetrics() == null ? 0 : getMetrics().hashCode())) * 31) + (getOptOut() == null ? 0 : getOptOut().hashCode())) * 31) + (getRequestId() == null ? 0 : getRequestId().hashCode())) * 31) + (getUser() != null ? getUser().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EndpointBatchItem)) {
            return false;
        }
        EndpointBatchItem endpointBatchItem = (EndpointBatchItem) obj;
        if ((endpointBatchItem.getAddress() == null) ^ (getAddress() == null)) {
            return false;
        }
        if (endpointBatchItem.getAddress() != null && !endpointBatchItem.getAddress().equals(getAddress())) {
            return false;
        }
        if ((endpointBatchItem.getAttributes() == null) ^ (getAttributes() == null)) {
            return false;
        }
        if (endpointBatchItem.getAttributes() != null && !endpointBatchItem.getAttributes().equals(getAttributes())) {
            return false;
        }
        if ((endpointBatchItem.getChannelType() == null) ^ (getChannelType() == null)) {
            return false;
        }
        if (endpointBatchItem.getChannelType() != null && !endpointBatchItem.getChannelType().equals(getChannelType())) {
            return false;
        }
        if ((endpointBatchItem.getDemographic() == null) ^ (getDemographic() == null)) {
            return false;
        }
        if (endpointBatchItem.getDemographic() != null && !endpointBatchItem.getDemographic().equals(getDemographic())) {
            return false;
        }
        if ((endpointBatchItem.getEffectiveDate() == null) ^ (getEffectiveDate() == null)) {
            return false;
        }
        if (endpointBatchItem.getEffectiveDate() != null && !endpointBatchItem.getEffectiveDate().equals(getEffectiveDate())) {
            return false;
        }
        if ((endpointBatchItem.getEndpointStatus() == null) ^ (getEndpointStatus() == null)) {
            return false;
        }
        if (endpointBatchItem.getEndpointStatus() != null && !endpointBatchItem.getEndpointStatus().equals(getEndpointStatus())) {
            return false;
        }
        if ((endpointBatchItem.getId() == null) ^ (getId() == null)) {
            return false;
        }
        if (endpointBatchItem.getId() != null && !endpointBatchItem.getId().equals(getId())) {
            return false;
        }
        if ((endpointBatchItem.getLocation() == null) ^ (getLocation() == null)) {
            return false;
        }
        if (endpointBatchItem.getLocation() != null && !endpointBatchItem.getLocation().equals(getLocation())) {
            return false;
        }
        if ((endpointBatchItem.getMetrics() == null) ^ (getMetrics() == null)) {
            return false;
        }
        if (endpointBatchItem.getMetrics() != null && !endpointBatchItem.getMetrics().equals(getMetrics())) {
            return false;
        }
        if ((endpointBatchItem.getOptOut() == null) ^ (getOptOut() == null)) {
            return false;
        }
        if (endpointBatchItem.getOptOut() != null && !endpointBatchItem.getOptOut().equals(getOptOut())) {
            return false;
        }
        if ((endpointBatchItem.getRequestId() == null) ^ (getRequestId() == null)) {
            return false;
        }
        if (endpointBatchItem.getRequestId() != null && !endpointBatchItem.getRequestId().equals(getRequestId())) {
            return false;
        }
        if ((endpointBatchItem.getUser() == null) ^ (getUser() == null)) {
            return false;
        }
        return endpointBatchItem.getUser() == null || endpointBatchItem.getUser().equals(getUser());
    }
}
