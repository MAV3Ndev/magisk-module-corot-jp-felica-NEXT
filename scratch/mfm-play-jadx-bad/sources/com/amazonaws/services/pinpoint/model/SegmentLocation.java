package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SegmentLocation implements Serializable {
    private SetDimension country;
    private GPSPointDimension gPSPoint;

    public SetDimension getCountry() {
        return this.country;
    }

    public void setCountry(SetDimension setDimension) {
        this.country = setDimension;
    }

    public SegmentLocation withCountry(SetDimension setDimension) {
        this.country = setDimension;
        return this;
    }

    public GPSPointDimension getGPSPoint() {
        return this.gPSPoint;
    }

    public void setGPSPoint(GPSPointDimension gPSPointDimension) {
        this.gPSPoint = gPSPointDimension;
    }

    public SegmentLocation withGPSPoint(GPSPointDimension gPSPointDimension) {
        this.gPSPoint = gPSPointDimension;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getCountry() != null) {
            sb.append("Country: " + getCountry() + ",");
        }
        if (getGPSPoint() != null) {
            sb.append("GPSPoint: " + getGPSPoint());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getCountry() == null ? 0 : getCountry().hashCode()) + 31) * 31) + (getGPSPoint() != null ? getGPSPoint().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SegmentLocation)) {
            return false;
        }
        SegmentLocation segmentLocation = (SegmentLocation) obj;
        if ((segmentLocation.getCountry() == null) ^ (getCountry() == null)) {
            return false;
        }
        if (segmentLocation.getCountry() != null && !segmentLocation.getCountry().equals(getCountry())) {
            return false;
        }
        if ((segmentLocation.getGPSPoint() == null) ^ (getGPSPoint() == null)) {
            return false;
        }
        return segmentLocation.getGPSPoint() == null || segmentLocation.getGPSPoint().equals(getGPSPoint());
    }
}
