package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class EventDimensions implements Serializable {
    private Map<String, AttributeDimension> attributes;
    private SetDimension eventType;
    private Map<String, MetricDimension> metrics;

    public Map<String, AttributeDimension> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Map<String, AttributeDimension> map) {
        this.attributes = map;
    }

    public EventDimensions withAttributes(Map<String, AttributeDimension> map) {
        this.attributes = map;
        return this;
    }

    public EventDimensions addAttributesEntry(String str, AttributeDimension attributeDimension) {
        if (this.attributes == null) {
            this.attributes = new HashMap();
        }
        if (this.attributes.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.attributes.put(str, attributeDimension);
        return this;
    }

    public EventDimensions clearAttributesEntries() {
        this.attributes = null;
        return this;
    }

    public SetDimension getEventType() {
        return this.eventType;
    }

    public void setEventType(SetDimension setDimension) {
        this.eventType = setDimension;
    }

    public EventDimensions withEventType(SetDimension setDimension) {
        this.eventType = setDimension;
        return this;
    }

    public Map<String, MetricDimension> getMetrics() {
        return this.metrics;
    }

    public void setMetrics(Map<String, MetricDimension> map) {
        this.metrics = map;
    }

    public EventDimensions withMetrics(Map<String, MetricDimension> map) {
        this.metrics = map;
        return this;
    }

    public EventDimensions addMetricsEntry(String str, MetricDimension metricDimension) {
        if (this.metrics == null) {
            this.metrics = new HashMap();
        }
        if (this.metrics.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.metrics.put(str, metricDimension);
        return this;
    }

    public EventDimensions clearMetricsEntries() {
        this.metrics = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getAttributes() != null) {
            sb.append("Attributes: " + getAttributes() + ",");
        }
        if (getEventType() != null) {
            sb.append("EventType: " + getEventType() + ",");
        }
        if (getMetrics() != null) {
            sb.append("Metrics: " + getMetrics());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((getAttributes() == null ? 0 : getAttributes().hashCode()) + 31) * 31) + (getEventType() == null ? 0 : getEventType().hashCode())) * 31) + (getMetrics() != null ? getMetrics().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EventDimensions)) {
            return false;
        }
        EventDimensions eventDimensions = (EventDimensions) obj;
        if ((eventDimensions.getAttributes() == null) ^ (getAttributes() == null)) {
            return false;
        }
        if (eventDimensions.getAttributes() != null && !eventDimensions.getAttributes().equals(getAttributes())) {
            return false;
        }
        if ((eventDimensions.getEventType() == null) ^ (getEventType() == null)) {
            return false;
        }
        if (eventDimensions.getEventType() != null && !eventDimensions.getEventType().equals(getEventType())) {
            return false;
        }
        if ((eventDimensions.getMetrics() == null) ^ (getMetrics() == null)) {
            return false;
        }
        return eventDimensions.getMetrics() == null || eventDimensions.getMetrics().equals(getMetrics());
    }
}
