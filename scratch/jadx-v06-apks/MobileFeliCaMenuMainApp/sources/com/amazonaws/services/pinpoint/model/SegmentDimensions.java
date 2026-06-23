package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SegmentDimensions implements Serializable {
    private Map<String, AttributeDimension> attributes;
    private SegmentBehaviors behavior;
    private SegmentDemographics demographic;
    private SegmentLocation location;
    private Map<String, MetricDimension> metrics;
    private Map<String, AttributeDimension> userAttributes;

    public Map<String, AttributeDimension> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, AttributeDimension> map) {
        this.attributes = map;
    }

    public SegmentDimensions withAttributes(Map<String, AttributeDimension> map) {
        this.attributes = map;
        return this;
    }

    public SegmentDimensions addAttributesEntry(String str, AttributeDimension attributeDimension) {
        if (this.attributes == null) {
            this.attributes = new HashMap();
        }
        if (this.attributes.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.attributes.put(str, attributeDimension);
        return this;
    }

    public SegmentDimensions clearAttributesEntries() {
        this.attributes = null;
        return this;
    }

    public SegmentBehaviors getBehavior() {
        return this.behavior;
    }

    public void setBehavior(SegmentBehaviors segmentBehaviors) {
        this.behavior = segmentBehaviors;
    }

    public SegmentDimensions withBehavior(SegmentBehaviors segmentBehaviors) {
        this.behavior = segmentBehaviors;
        return this;
    }

    public SegmentDemographics getDemographic() {
        return this.demographic;
    }

    public void setDemographic(SegmentDemographics segmentDemographics) {
        this.demographic = segmentDemographics;
    }

    public SegmentDimensions withDemographic(SegmentDemographics segmentDemographics) {
        this.demographic = segmentDemographics;
        return this;
    }

    public SegmentLocation getLocation() {
        return this.location;
    }

    public void setLocation(SegmentLocation segmentLocation) {
        this.location = segmentLocation;
    }

    public SegmentDimensions withLocation(SegmentLocation segmentLocation) {
        this.location = segmentLocation;
        return this;
    }

    public Map<String, MetricDimension> getMetrics() {
        return this.metrics;
    }

    public void setMetrics(Map<String, MetricDimension> map) {
        this.metrics = map;
    }

    public SegmentDimensions withMetrics(Map<String, MetricDimension> map) {
        this.metrics = map;
        return this;
    }

    public SegmentDimensions addMetricsEntry(String str, MetricDimension metricDimension) {
        if (this.metrics == null) {
            this.metrics = new HashMap();
        }
        if (this.metrics.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.metrics.put(str, metricDimension);
        return this;
    }

    public SegmentDimensions clearMetricsEntries() {
        this.metrics = null;
        return this;
    }

    public Map<String, AttributeDimension> getUserAttributes() {
        return this.userAttributes;
    }

    public void setUserAttributes(Map<String, AttributeDimension> map) {
        this.userAttributes = map;
    }

    public SegmentDimensions withUserAttributes(Map<String, AttributeDimension> map) {
        this.userAttributes = map;
        return this;
    }

    public SegmentDimensions addUserAttributesEntry(String str, AttributeDimension attributeDimension) {
        if (this.userAttributes == null) {
            this.userAttributes = new HashMap();
        }
        if (this.userAttributes.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.userAttributes.put(str, attributeDimension);
        return this;
    }

    public SegmentDimensions clearUserAttributesEntries() {
        this.userAttributes = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAttributes() != null) {
            sb.append("Attributes: " + getAttributes() + ",");
        }
        if (getBehavior() != null) {
            sb.append("Behavior: " + getBehavior() + ",");
        }
        if (getDemographic() != null) {
            sb.append("Demographic: " + getDemographic() + ",");
        }
        if (getLocation() != null) {
            sb.append("Location: " + getLocation() + ",");
        }
        if (getMetrics() != null) {
            sb.append("Metrics: " + getMetrics() + ",");
        }
        if (getUserAttributes() != null) {
            sb.append("UserAttributes: " + getUserAttributes());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((getAttributes() == null ? 0 : getAttributes().hashCode()) + 31) * 31) + (getBehavior() == null ? 0 : getBehavior().hashCode())) * 31) + (getDemographic() == null ? 0 : getDemographic().hashCode())) * 31) + (getLocation() == null ? 0 : getLocation().hashCode())) * 31) + (getMetrics() == null ? 0 : getMetrics().hashCode())) * 31) + (getUserAttributes() != null ? getUserAttributes().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SegmentDimensions)) {
            return false;
        }
        SegmentDimensions segmentDimensions = (SegmentDimensions) obj;
        if ((segmentDimensions.getAttributes() == null) ^ (getAttributes() == null)) {
            return false;
        }
        if (segmentDimensions.getAttributes() != null && !segmentDimensions.getAttributes().equals(getAttributes())) {
            return false;
        }
        if ((segmentDimensions.getBehavior() == null) ^ (getBehavior() == null)) {
            return false;
        }
        if (segmentDimensions.getBehavior() != null && !segmentDimensions.getBehavior().equals(getBehavior())) {
            return false;
        }
        if ((segmentDimensions.getDemographic() == null) ^ (getDemographic() == null)) {
            return false;
        }
        if (segmentDimensions.getDemographic() != null && !segmentDimensions.getDemographic().equals(getDemographic())) {
            return false;
        }
        if ((segmentDimensions.getLocation() == null) ^ (getLocation() == null)) {
            return false;
        }
        if (segmentDimensions.getLocation() != null && !segmentDimensions.getLocation().equals(getLocation())) {
            return false;
        }
        if ((segmentDimensions.getMetrics() == null) ^ (getMetrics() == null)) {
            return false;
        }
        if (segmentDimensions.getMetrics() != null && !segmentDimensions.getMetrics().equals(getMetrics())) {
            return false;
        }
        if ((segmentDimensions.getUserAttributes() == null) ^ (getUserAttributes() == null)) {
            return false;
        }
        return segmentDimensions.getUserAttributes() == null || segmentDimensions.getUserAttributes().equals(getUserAttributes());
    }
}
