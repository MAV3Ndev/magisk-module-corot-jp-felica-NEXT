package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class EndpointDemographic implements Serializable {
    private String appVersion;
    private String locale;
    private String make;
    private String model;
    private String modelVersion;
    private String platform;
    private String platformVersion;
    private String timezone;

    public String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(String str) {
        this.appVersion = str;
    }

    public EndpointDemographic withAppVersion(String str) {
        this.appVersion = str;
        return this;
    }

    public String getLocale() {
        return this.locale;
    }

    public void setLocale(String str) {
        this.locale = str;
    }

    public EndpointDemographic withLocale(String str) {
        this.locale = str;
        return this;
    }

    public String getMake() {
        return this.make;
    }

    public void setMake(String str) {
        this.make = str;
    }

    public EndpointDemographic withMake(String str) {
        this.make = str;
        return this;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public EndpointDemographic withModel(String str) {
        this.model = str;
        return this;
    }

    public String getModelVersion() {
        return this.modelVersion;
    }

    public void setModelVersion(String str) {
        this.modelVersion = str;
    }

    public EndpointDemographic withModelVersion(String str) {
        this.modelVersion = str;
        return this;
    }

    public String getPlatform() {
        return this.platform;
    }

    public void setPlatform(String str) {
        this.platform = str;
    }

    public EndpointDemographic withPlatform(String str) {
        this.platform = str;
        return this;
    }

    public String getPlatformVersion() {
        return this.platformVersion;
    }

    public void setPlatformVersion(String str) {
        this.platformVersion = str;
    }

    public EndpointDemographic withPlatformVersion(String str) {
        this.platformVersion = str;
        return this;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public void setTimezone(String str) {
        this.timezone = str;
    }

    public EndpointDemographic withTimezone(String str) {
        this.timezone = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getAppVersion() != null) {
            sb.append("AppVersion: " + getAppVersion() + ",");
        }
        if (getLocale() != null) {
            sb.append("Locale: " + getLocale() + ",");
        }
        if (getMake() != null) {
            sb.append("Make: " + getMake() + ",");
        }
        if (getModel() != null) {
            sb.append("Model: " + getModel() + ",");
        }
        if (getModelVersion() != null) {
            sb.append("ModelVersion: " + getModelVersion() + ",");
        }
        if (getPlatform() != null) {
            sb.append("Platform: " + getPlatform() + ",");
        }
        if (getPlatformVersion() != null) {
            sb.append("PlatformVersion: " + getPlatformVersion() + ",");
        }
        if (getTimezone() != null) {
            sb.append("Timezone: " + getTimezone());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((((getAppVersion() == null ? 0 : getAppVersion().hashCode()) + 31) * 31) + (getLocale() == null ? 0 : getLocale().hashCode())) * 31) + (getMake() == null ? 0 : getMake().hashCode())) * 31) + (getModel() == null ? 0 : getModel().hashCode())) * 31) + (getModelVersion() == null ? 0 : getModelVersion().hashCode())) * 31) + (getPlatform() == null ? 0 : getPlatform().hashCode())) * 31) + (getPlatformVersion() == null ? 0 : getPlatformVersion().hashCode())) * 31) + (getTimezone() != null ? getTimezone().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EndpointDemographic)) {
            return false;
        }
        EndpointDemographic endpointDemographic = (EndpointDemographic) obj;
        if ((endpointDemographic.getAppVersion() == null) ^ (getAppVersion() == null)) {
            return false;
        }
        if (endpointDemographic.getAppVersion() != null && !endpointDemographic.getAppVersion().equals(getAppVersion())) {
            return false;
        }
        if ((endpointDemographic.getLocale() == null) ^ (getLocale() == null)) {
            return false;
        }
        if (endpointDemographic.getLocale() != null && !endpointDemographic.getLocale().equals(getLocale())) {
            return false;
        }
        if ((endpointDemographic.getMake() == null) ^ (getMake() == null)) {
            return false;
        }
        if (endpointDemographic.getMake() != null && !endpointDemographic.getMake().equals(getMake())) {
            return false;
        }
        if ((endpointDemographic.getModel() == null) ^ (getModel() == null)) {
            return false;
        }
        if (endpointDemographic.getModel() != null && !endpointDemographic.getModel().equals(getModel())) {
            return false;
        }
        if ((endpointDemographic.getModelVersion() == null) ^ (getModelVersion() == null)) {
            return false;
        }
        if (endpointDemographic.getModelVersion() != null && !endpointDemographic.getModelVersion().equals(getModelVersion())) {
            return false;
        }
        if ((endpointDemographic.getPlatform() == null) ^ (getPlatform() == null)) {
            return false;
        }
        if (endpointDemographic.getPlatform() != null && !endpointDemographic.getPlatform().equals(getPlatform())) {
            return false;
        }
        if ((endpointDemographic.getPlatformVersion() == null) ^ (getPlatformVersion() == null)) {
            return false;
        }
        if (endpointDemographic.getPlatformVersion() != null && !endpointDemographic.getPlatformVersion().equals(getPlatformVersion())) {
            return false;
        }
        if ((endpointDemographic.getTimezone() == null) ^ (getTimezone() == null)) {
            return false;
        }
        return endpointDemographic.getTimezone() == null || endpointDemographic.getTimezone().equals(getTimezone());
    }
}
