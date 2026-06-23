package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class EndpointLocation implements Serializable {
    private String city;
    private String country;
    private Double latitude;
    private Double longitude;
    private String postalCode;
    private String region;

    public String getCity() {
        return this.city;
    }

    public void setCity(String str) {
        this.city = str;
    }

    public EndpointLocation withCity(String str) {
        this.city = str;
        return this;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String str) {
        this.country = str;
    }

    public EndpointLocation withCountry(String str) {
        this.country = str;
        return this;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double d) {
        this.latitude = d;
    }

    public EndpointLocation withLatitude(Double d) {
        this.latitude = d;
        return this;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double d) {
        this.longitude = d;
    }

    public EndpointLocation withLongitude(Double d) {
        this.longitude = d;
        return this;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String str) {
        this.postalCode = str;
    }

    public EndpointLocation withPostalCode(String str) {
        this.postalCode = str;
        return this;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String str) {
        this.region = str;
    }

    public EndpointLocation withRegion(String str) {
        this.region = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getCity() != null) {
            sb.append("City: " + getCity() + ",");
        }
        if (getCountry() != null) {
            sb.append("Country: " + getCountry() + ",");
        }
        if (getLatitude() != null) {
            sb.append("Latitude: " + getLatitude() + ",");
        }
        if (getLongitude() != null) {
            sb.append("Longitude: " + getLongitude() + ",");
        }
        if (getPostalCode() != null) {
            sb.append("PostalCode: " + getPostalCode() + ",");
        }
        if (getRegion() != null) {
            sb.append("Region: " + getRegion());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((getCity() == null ? 0 : getCity().hashCode()) + 31) * 31) + (getCountry() == null ? 0 : getCountry().hashCode())) * 31) + (getLatitude() == null ? 0 : getLatitude().hashCode())) * 31) + (getLongitude() == null ? 0 : getLongitude().hashCode())) * 31) + (getPostalCode() == null ? 0 : getPostalCode().hashCode())) * 31) + (getRegion() != null ? getRegion().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EndpointLocation)) {
            return false;
        }
        EndpointLocation endpointLocation = (EndpointLocation) obj;
        if ((endpointLocation.getCity() == null) ^ (getCity() == null)) {
            return false;
        }
        if (endpointLocation.getCity() != null && !endpointLocation.getCity().equals(getCity())) {
            return false;
        }
        if ((endpointLocation.getCountry() == null) ^ (getCountry() == null)) {
            return false;
        }
        if (endpointLocation.getCountry() != null && !endpointLocation.getCountry().equals(getCountry())) {
            return false;
        }
        if ((endpointLocation.getLatitude() == null) ^ (getLatitude() == null)) {
            return false;
        }
        if (endpointLocation.getLatitude() != null && !endpointLocation.getLatitude().equals(getLatitude())) {
            return false;
        }
        if ((endpointLocation.getLongitude() == null) ^ (getLongitude() == null)) {
            return false;
        }
        if (endpointLocation.getLongitude() != null && !endpointLocation.getLongitude().equals(getLongitude())) {
            return false;
        }
        if ((endpointLocation.getPostalCode() == null) ^ (getPostalCode() == null)) {
            return false;
        }
        if (endpointLocation.getPostalCode() != null && !endpointLocation.getPostalCode().equals(getPostalCode())) {
            return false;
        }
        if ((endpointLocation.getRegion() == null) ^ (getRegion() == null)) {
            return false;
        }
        return endpointLocation.getRegion() == null || endpointLocation.getRegion().equals(getRegion());
    }
}
