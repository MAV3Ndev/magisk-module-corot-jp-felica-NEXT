package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class WriteSegmentRequest implements Serializable {
    private SegmentDimensions dimensions;
    private String name;
    private SegmentGroupList segmentGroups;

    public SegmentDimensions getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(SegmentDimensions segmentDimensions) {
        this.dimensions = segmentDimensions;
    }

    public WriteSegmentRequest withDimensions(SegmentDimensions segmentDimensions) {
        this.dimensions = segmentDimensions;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public WriteSegmentRequest withName(String str) {
        this.name = str;
        return this;
    }

    public SegmentGroupList getSegmentGroups() {
        return this.segmentGroups;
    }

    public void setSegmentGroups(SegmentGroupList segmentGroupList) {
        this.segmentGroups = segmentGroupList;
    }

    public WriteSegmentRequest withSegmentGroups(SegmentGroupList segmentGroupList) {
        this.segmentGroups = segmentGroupList;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getDimensions() != null) {
            sb.append("Dimensions: " + getDimensions() + ",");
        }
        if (getName() != null) {
            sb.append("Name: " + getName() + ",");
        }
        if (getSegmentGroups() != null) {
            sb.append("SegmentGroups: " + getSegmentGroups());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((getDimensions() == null ? 0 : getDimensions().hashCode()) + 31) * 31) + (getName() == null ? 0 : getName().hashCode())) * 31) + (getSegmentGroups() != null ? getSegmentGroups().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof WriteSegmentRequest)) {
            return false;
        }
        WriteSegmentRequest writeSegmentRequest = (WriteSegmentRequest) obj;
        if ((writeSegmentRequest.getDimensions() == null) ^ (getDimensions() == null)) {
            return false;
        }
        if (writeSegmentRequest.getDimensions() != null && !writeSegmentRequest.getDimensions().equals(getDimensions())) {
            return false;
        }
        if ((writeSegmentRequest.getName() == null) ^ (getName() == null)) {
            return false;
        }
        if (writeSegmentRequest.getName() != null && !writeSegmentRequest.getName().equals(getName())) {
            return false;
        }
        if ((writeSegmentRequest.getSegmentGroups() == null) ^ (getSegmentGroups() == null)) {
            return false;
        }
        return writeSegmentRequest.getSegmentGroups() == null || writeSegmentRequest.getSegmentGroups().equals(getSegmentGroups());
    }
}
