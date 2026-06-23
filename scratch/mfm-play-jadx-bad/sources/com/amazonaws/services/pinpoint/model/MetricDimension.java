package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class MetricDimension implements Serializable {
    private String comparisonOperator;
    private Double value;

    public String getComparisonOperator() {
        return this.comparisonOperator;
    }

    public void setComparisonOperator(String str) {
        this.comparisonOperator = str;
    }

    public MetricDimension withComparisonOperator(String str) {
        this.comparisonOperator = str;
        return this;
    }

    public Double getValue() {
        return this.value;
    }

    public void setValue(Double d) {
        this.value = d;
    }

    public MetricDimension withValue(Double d) {
        this.value = d;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getComparisonOperator() != null) {
            sb.append("ComparisonOperator: " + getComparisonOperator() + ",");
        }
        if (getValue() != null) {
            sb.append("Value: " + getValue());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getComparisonOperator() == null ? 0 : getComparisonOperator().hashCode()) + 31) * 31) + (getValue() != null ? getValue().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MetricDimension)) {
            return false;
        }
        MetricDimension metricDimension = (MetricDimension) obj;
        if ((metricDimension.getComparisonOperator() == null) ^ (getComparisonOperator() == null)) {
            return false;
        }
        if (metricDimension.getComparisonOperator() != null && !metricDimension.getComparisonOperator().equals(getComparisonOperator())) {
            return false;
        }
        if ((metricDimension.getValue() == null) ^ (getValue() == null)) {
            return false;
        }
        return metricDimension.getValue() == null || metricDimension.getValue().equals(getValue());
    }
}
