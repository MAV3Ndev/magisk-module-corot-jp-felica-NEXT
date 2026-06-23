package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GPSPointDimension implements Serializable {
    private GPSCoordinates coordinates;
    private Double rangeInKilometers;

    public GPSCoordinates getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(GPSCoordinates gPSCoordinates) {
        this.coordinates = gPSCoordinates;
    }

    public GPSPointDimension withCoordinates(GPSCoordinates gPSCoordinates) {
        this.coordinates = gPSCoordinates;
        return this;
    }

    public Double getRangeInKilometers() {
        return this.rangeInKilometers;
    }

    public void setRangeInKilometers(Double d) {
        this.rangeInKilometers = d;
    }

    public GPSPointDimension withRangeInKilometers(Double d) {
        this.rangeInKilometers = d;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getCoordinates() != null) {
            sb.append("Coordinates: " + getCoordinates() + ",");
        }
        if (getRangeInKilometers() != null) {
            sb.append("RangeInKilometers: " + getRangeInKilometers());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getCoordinates() == null ? 0 : getCoordinates().hashCode()) + 31) * 31) + (getRangeInKilometers() != null ? getRangeInKilometers().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GPSPointDimension)) {
            return false;
        }
        GPSPointDimension gPSPointDimension = (GPSPointDimension) obj;
        if ((gPSPointDimension.getCoordinates() == null) ^ (getCoordinates() == null)) {
            return false;
        }
        if (gPSPointDimension.getCoordinates() != null && !gPSPointDimension.getCoordinates().equals(getCoordinates())) {
            return false;
        }
        if ((gPSPointDimension.getRangeInKilometers() == null) ^ (getRangeInKilometers() == null)) {
            return false;
        }
        return gPSPointDimension.getRangeInKilometers() == null || gPSPointDimension.getRangeInKilometers().equals(getRangeInKilometers());
    }
}
