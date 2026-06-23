package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CampaignEventFilter implements Serializable {
    private EventDimensions dimensions;
    private String filterType;

    public EventDimensions getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(EventDimensions eventDimensions) {
        this.dimensions = eventDimensions;
    }

    public CampaignEventFilter withDimensions(EventDimensions eventDimensions) {
        this.dimensions = eventDimensions;
        return this;
    }

    public String getFilterType() {
        return this.filterType;
    }

    public void setFilterType(String str) {
        this.filterType = str;
    }

    public CampaignEventFilter withFilterType(String str) {
        this.filterType = str;
        return this;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType.toString();
    }

    public CampaignEventFilter withFilterType(FilterType filterType) {
        this.filterType = filterType.toString();
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getDimensions() != null) {
            sb.append("Dimensions: " + getDimensions() + ",");
        }
        if (getFilterType() != null) {
            sb.append("FilterType: " + getFilterType());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getDimensions() == null ? 0 : getDimensions().hashCode()) + 31) * 31) + (getFilterType() != null ? getFilterType().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CampaignEventFilter)) {
            return false;
        }
        CampaignEventFilter campaignEventFilter = (CampaignEventFilter) obj;
        if ((campaignEventFilter.getDimensions() == null) ^ (getDimensions() == null)) {
            return false;
        }
        if (campaignEventFilter.getDimensions() != null && !campaignEventFilter.getDimensions().equals(getDimensions())) {
            return false;
        }
        if ((campaignEventFilter.getFilterType() == null) ^ (getFilterType() == null)) {
            return false;
        }
        return campaignEventFilter.getFilterType() == null || campaignEventFilter.getFilterType().equals(getFilterType());
    }
}
