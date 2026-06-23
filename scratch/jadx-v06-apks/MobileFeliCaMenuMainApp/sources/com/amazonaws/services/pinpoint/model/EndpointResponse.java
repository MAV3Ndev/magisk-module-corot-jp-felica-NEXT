package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class EndpointResponse implements Serializable {
    private String address;
    private String applicationId;
    private Map<String, List<String>> attributes;
    private String channelType;
    private String cohortId;
    private String creationDate;
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

    public EndpointResponse withAddress(String str) {
        this.address = str;
        return this;
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public EndpointResponse withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public Map<String, List<String>> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, List<String>> map) {
        this.attributes = map;
    }

    public EndpointResponse withAttributes(Map<String, List<String>> map) {
        this.attributes = map;
        return this;
    }

    public EndpointResponse addAttributesEntry(String str, List<String> list) {
        if (this.attributes == null) {
            this.attributes = new HashMap();
        }
        if (this.attributes.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.attributes.put(str, list);
        return this;
    }

    public EndpointResponse clearAttributesEntries() {
        this.attributes = null;
        return this;
    }

    public String getChannelType() {
        return this.channelType;
    }

    public void setChannelType(String str) {
        this.channelType = str;
    }

    public EndpointResponse withChannelType(String str) {
        this.channelType = str;
        return this;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType.toString();
    }

    public EndpointResponse withChannelType(ChannelType channelType) {
        this.channelType = channelType.toString();
        return this;
    }

    public String getCohortId() {
        return this.cohortId;
    }

    public void setCohortId(String str) {
        this.cohortId = str;
    }

    public EndpointResponse withCohortId(String str) {
        this.cohortId = str;
        return this;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(String str) {
        this.creationDate = str;
    }

    public EndpointResponse withCreationDate(String str) {
        this.creationDate = str;
        return this;
    }

    public EndpointDemographic getDemographic() {
        return this.demographic;
    }

    public void setDemographic(EndpointDemographic endpointDemographic) {
        this.demographic = endpointDemographic;
    }

    public EndpointResponse withDemographic(EndpointDemographic endpointDemographic) {
        this.demographic = endpointDemographic;
        return this;
    }

    public String getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(String str) {
        this.effectiveDate = str;
    }

    public EndpointResponse withEffectiveDate(String str) {
        this.effectiveDate = str;
        return this;
    }

    public String getEndpointStatus() {
        return this.endpointStatus;
    }

    public void setEndpointStatus(String str) {
        this.endpointStatus = str;
    }

    public EndpointResponse withEndpointStatus(String str) {
        this.endpointStatus = str;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public EndpointResponse withId(String str) {
        this.id = str;
        return this;
    }

    public EndpointLocation getLocation() {
        return this.location;
    }

    public void setLocation(EndpointLocation endpointLocation) {
        this.location = endpointLocation;
    }

    public EndpointResponse withLocation(EndpointLocation endpointLocation) {
        this.location = endpointLocation;
        return this;
    }

    public Map<String, Double> getMetrics() {
        return this.metrics;
    }

    public void setMetrics(Map<String, Double> map) {
        this.metrics = map;
    }

    public EndpointResponse withMetrics(Map<String, Double> map) {
        this.metrics = map;
        return this;
    }

    public EndpointResponse addMetricsEntry(String str, Double d) {
        if (this.metrics == null) {
            this.metrics = new HashMap();
        }
        if (this.metrics.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.metrics.put(str, d);
        return this;
    }

    public EndpointResponse clearMetricsEntries() {
        this.metrics = null;
        return this;
    }

    public String getOptOut() {
        return this.optOut;
    }

    public void setOptOut(String str) {
        this.optOut = str;
    }

    public EndpointResponse withOptOut(String str) {
        this.optOut = str;
        return this;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String str) {
        this.requestId = str;
    }

    public EndpointResponse withRequestId(String str) {
        this.requestId = str;
        return this;
    }

    public EndpointUser getUser() {
        return this.user;
    }

    public void setUser(EndpointUser endpointUser) {
        this.user = endpointUser;
    }

    public EndpointResponse withUser(EndpointUser endpointUser) {
        this.user = endpointUser;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAddress() != null) {
            sb.append("Address: " + getAddress() + ",");
        }
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getAttributes() != null) {
            sb.append("Attributes: " + getAttributes() + ",");
        }
        if (getChannelType() != null) {
            sb.append("ChannelType: " + getChannelType() + ",");
        }
        if (getCohortId() != null) {
            sb.append("CohortId: " + getCohortId() + ",");
        }
        if (getCreationDate() != null) {
            sb.append("CreationDate: " + getCreationDate() + ",");
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
        return (((((((((((((((((((((((((((((getAddress() == null ? 0 : getAddress().hashCode()) + 31) * 31) + (getApplicationId() == null ? 0 : getApplicationId().hashCode())) * 31) + (getAttributes() == null ? 0 : getAttributes().hashCode())) * 31) + (getChannelType() == null ? 0 : getChannelType().hashCode())) * 31) + (getCohortId() == null ? 0 : getCohortId().hashCode())) * 31) + (getCreationDate() == null ? 0 : getCreationDate().hashCode())) * 31) + (getDemographic() == null ? 0 : getDemographic().hashCode())) * 31) + (getEffectiveDate() == null ? 0 : getEffectiveDate().hashCode())) * 31) + (getEndpointStatus() == null ? 0 : getEndpointStatus().hashCode())) * 31) + (getId() == null ? 0 : getId().hashCode())) * 31) + (getLocation() == null ? 0 : getLocation().hashCode())) * 31) + (getMetrics() == null ? 0 : getMetrics().hashCode())) * 31) + (getOptOut() == null ? 0 : getOptOut().hashCode())) * 31) + (getRequestId() == null ? 0 : getRequestId().hashCode())) * 31) + (getUser() != null ? getUser().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EndpointResponse)) {
            return false;
        }
        EndpointResponse endpointResponse = (EndpointResponse) obj;
        if ((endpointResponse.getAddress() == null) ^ (getAddress() == null)) {
            return false;
        }
        if (endpointResponse.getAddress() != null && !endpointResponse.getAddress().equals(getAddress())) {
            return false;
        }
        if ((endpointResponse.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (endpointResponse.getApplicationId() != null && !endpointResponse.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((endpointResponse.getAttributes() == null) ^ (getAttributes() == null)) {
            return false;
        }
        if (endpointResponse.getAttributes() != null && !endpointResponse.getAttributes().equals(getAttributes())) {
            return false;
        }
        if ((endpointResponse.getChannelType() == null) ^ (getChannelType() == null)) {
            return false;
        }
        if (endpointResponse.getChannelType() != null && !endpointResponse.getChannelType().equals(getChannelType())) {
            return false;
        }
        if ((endpointResponse.getCohortId() == null) ^ (getCohortId() == null)) {
            return false;
        }
        if (endpointResponse.getCohortId() != null && !endpointResponse.getCohortId().equals(getCohortId())) {
            return false;
        }
        if ((endpointResponse.getCreationDate() == null) ^ (getCreationDate() == null)) {
            return false;
        }
        if (endpointResponse.getCreationDate() != null && !endpointResponse.getCreationDate().equals(getCreationDate())) {
            return false;
        }
        if ((endpointResponse.getDemographic() == null) ^ (getDemographic() == null)) {
            return false;
        }
        if (endpointResponse.getDemographic() != null && !endpointResponse.getDemographic().equals(getDemographic())) {
            return false;
        }
        if ((endpointResponse.getEffectiveDate() == null) ^ (getEffectiveDate() == null)) {
            return false;
        }
        if (endpointResponse.getEffectiveDate() != null && !endpointResponse.getEffectiveDate().equals(getEffectiveDate())) {
            return false;
        }
        if ((endpointResponse.getEndpointStatus() == null) ^ (getEndpointStatus() == null)) {
            return false;
        }
        if (endpointResponse.getEndpointStatus() != null && !endpointResponse.getEndpointStatus().equals(getEndpointStatus())) {
            return false;
        }
        if ((endpointResponse.getId() == null) ^ (getId() == null)) {
            return false;
        }
        if (endpointResponse.getId() != null && !endpointResponse.getId().equals(getId())) {
            return false;
        }
        if ((endpointResponse.getLocation() == null) ^ (getLocation() == null)) {
            return false;
        }
        if (endpointResponse.getLocation() != null && !endpointResponse.getLocation().equals(getLocation())) {
            return false;
        }
        if ((endpointResponse.getMetrics() == null) ^ (getMetrics() == null)) {
            return false;
        }
        if (endpointResponse.getMetrics() != null && !endpointResponse.getMetrics().equals(getMetrics())) {
            return false;
        }
        if ((endpointResponse.getOptOut() == null) ^ (getOptOut() == null)) {
            return false;
        }
        if (endpointResponse.getOptOut() != null && !endpointResponse.getOptOut().equals(getOptOut())) {
            return false;
        }
        if ((endpointResponse.getRequestId() == null) ^ (getRequestId() == null)) {
            return false;
        }
        if (endpointResponse.getRequestId() != null && !endpointResponse.getRequestId().equals(getRequestId())) {
            return false;
        }
        if ((endpointResponse.getUser() == null) ^ (getUser() == null)) {
            return false;
        }
        return endpointResponse.getUser() == null || endpointResponse.getUser().equals(getUser());
    }
}
