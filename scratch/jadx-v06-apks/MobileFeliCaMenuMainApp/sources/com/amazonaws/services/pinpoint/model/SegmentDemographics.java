package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SegmentDemographics implements Serializable {
    private SetDimension appVersion;
    private SetDimension channel;
    private SetDimension deviceType;
    private SetDimension make;
    private SetDimension model;
    private SetDimension platform;

    public SetDimension getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(SetDimension setDimension) {
        this.appVersion = setDimension;
    }

    public SegmentDemographics withAppVersion(SetDimension setDimension) {
        this.appVersion = setDimension;
        return this;
    }

    public SetDimension getChannel() {
        return this.channel;
    }

    public void setChannel(SetDimension setDimension) {
        this.channel = setDimension;
    }

    public SegmentDemographics withChannel(SetDimension setDimension) {
        this.channel = setDimension;
        return this;
    }

    public SetDimension getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(SetDimension setDimension) {
        this.deviceType = setDimension;
    }

    public SegmentDemographics withDeviceType(SetDimension setDimension) {
        this.deviceType = setDimension;
        return this;
    }

    public SetDimension getMake() {
        return this.make;
    }

    public void setMake(SetDimension setDimension) {
        this.make = setDimension;
    }

    public SegmentDemographics withMake(SetDimension setDimension) {
        this.make = setDimension;
        return this;
    }

    public SetDimension getModel() {
        return this.model;
    }

    public void setModel(SetDimension setDimension) {
        this.model = setDimension;
    }

    public SegmentDemographics withModel(SetDimension setDimension) {
        this.model = setDimension;
        return this;
    }

    public SetDimension getPlatform() {
        return this.platform;
    }

    public void setPlatform(SetDimension setDimension) {
        this.platform = setDimension;
    }

    public SegmentDemographics withPlatform(SetDimension setDimension) {
        this.platform = setDimension;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAppVersion() != null) {
            sb.append("AppVersion: " + getAppVersion() + ",");
        }
        if (getChannel() != null) {
            sb.append("Channel: " + getChannel() + ",");
        }
        if (getDeviceType() != null) {
            sb.append("DeviceType: " + getDeviceType() + ",");
        }
        if (getMake() != null) {
            sb.append("Make: " + getMake() + ",");
        }
        if (getModel() != null) {
            sb.append("Model: " + getModel() + ",");
        }
        if (getPlatform() != null) {
            sb.append("Platform: " + getPlatform());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((getAppVersion() == null ? 0 : getAppVersion().hashCode()) + 31) * 31) + (getChannel() == null ? 0 : getChannel().hashCode())) * 31) + (getDeviceType() == null ? 0 : getDeviceType().hashCode())) * 31) + (getMake() == null ? 0 : getMake().hashCode())) * 31) + (getModel() == null ? 0 : getModel().hashCode())) * 31) + (getPlatform() != null ? getPlatform().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SegmentDemographics)) {
            return false;
        }
        SegmentDemographics segmentDemographics = (SegmentDemographics) obj;
        if ((segmentDemographics.getAppVersion() == null) ^ (getAppVersion() == null)) {
            return false;
        }
        if (segmentDemographics.getAppVersion() != null && !segmentDemographics.getAppVersion().equals(getAppVersion())) {
            return false;
        }
        if ((segmentDemographics.getChannel() == null) ^ (getChannel() == null)) {
            return false;
        }
        if (segmentDemographics.getChannel() != null && !segmentDemographics.getChannel().equals(getChannel())) {
            return false;
        }
        if ((segmentDemographics.getDeviceType() == null) ^ (getDeviceType() == null)) {
            return false;
        }
        if (segmentDemographics.getDeviceType() != null && !segmentDemographics.getDeviceType().equals(getDeviceType())) {
            return false;
        }
        if ((segmentDemographics.getMake() == null) ^ (getMake() == null)) {
            return false;
        }
        if (segmentDemographics.getMake() != null && !segmentDemographics.getMake().equals(getMake())) {
            return false;
        }
        if ((segmentDemographics.getModel() == null) ^ (getModel() == null)) {
            return false;
        }
        if (segmentDemographics.getModel() != null && !segmentDemographics.getModel().equals(getModel())) {
            return false;
        }
        if ((segmentDemographics.getPlatform() == null) ^ (getPlatform() == null)) {
            return false;
        }
        return segmentDemographics.getPlatform() == null || segmentDemographics.getPlatform().equals(getPlatform());
    }
}
