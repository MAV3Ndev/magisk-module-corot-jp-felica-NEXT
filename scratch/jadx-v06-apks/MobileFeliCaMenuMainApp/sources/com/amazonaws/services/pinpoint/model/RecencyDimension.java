package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class RecencyDimension implements Serializable {
    private String duration;
    private String recencyType;

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String str) {
        this.duration = str;
    }

    public RecencyDimension withDuration(String str) {
        this.duration = str;
        return this;
    }

    public void setDuration(Duration duration) {
        this.duration = duration.toString();
    }

    public RecencyDimension withDuration(Duration duration) {
        this.duration = duration.toString();
        return this;
    }

    public String getRecencyType() {
        return this.recencyType;
    }

    public void setRecencyType(String str) {
        this.recencyType = str;
    }

    public RecencyDimension withRecencyType(String str) {
        this.recencyType = str;
        return this;
    }

    public void setRecencyType(RecencyType recencyType) {
        this.recencyType = recencyType.toString();
    }

    public RecencyDimension withRecencyType(RecencyType recencyType) {
        this.recencyType = recencyType.toString();
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getDuration() != null) {
            sb.append("Duration: " + getDuration() + ",");
        }
        if (getRecencyType() != null) {
            sb.append("RecencyType: " + getRecencyType());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getDuration() == null ? 0 : getDuration().hashCode()) + 31) * 31) + (getRecencyType() != null ? getRecencyType().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof RecencyDimension)) {
            return false;
        }
        RecencyDimension recencyDimension = (RecencyDimension) obj;
        if ((recencyDimension.getDuration() == null) ^ (getDuration() == null)) {
            return false;
        }
        if (recencyDimension.getDuration() != null && !recencyDimension.getDuration().equals(getDuration())) {
            return false;
        }
        if ((recencyDimension.getRecencyType() == null) ^ (getRecencyType() == null)) {
            return false;
        }
        return recencyDimension.getRecencyType() == null || recencyDimension.getRecencyType().equals(getRecencyType());
    }
}
