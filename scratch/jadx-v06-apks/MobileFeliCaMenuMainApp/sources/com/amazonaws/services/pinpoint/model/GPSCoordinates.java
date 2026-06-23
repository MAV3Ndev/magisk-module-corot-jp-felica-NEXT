package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GPSCoordinates implements Serializable {
    private Double latitude;
    private Double longitude;

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double d) {
        this.latitude = d;
    }

    public GPSCoordinates withLatitude(Double d) {
        this.latitude = d;
        return this;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double d) {
        this.longitude = d;
    }

    public GPSCoordinates withLongitude(Double d) {
        this.longitude = d;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getLatitude() != null) {
            sb.append("Latitude: " + getLatitude() + ",");
        }
        if (getLongitude() != null) {
            sb.append("Longitude: " + getLongitude());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getLatitude() == null ? 0 : getLatitude().hashCode()) + 31) * 31) + (getLongitude() != null ? getLongitude().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GPSCoordinates)) {
            return false;
        }
        GPSCoordinates gPSCoordinates = (GPSCoordinates) obj;
        if ((gPSCoordinates.getLatitude() == null) ^ (getLatitude() == null)) {
            return false;
        }
        if (gPSCoordinates.getLatitude() != null && !gPSCoordinates.getLatitude().equals(getLatitude())) {
            return false;
        }
        if ((gPSCoordinates.getLongitude() == null) ^ (getLongitude() == null)) {
            return false;
        }
        return gPSCoordinates.getLongitude() == null || gPSCoordinates.getLongitude().equals(getLongitude());
    }
}
