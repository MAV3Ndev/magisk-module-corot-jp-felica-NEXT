package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SegmentReference implements Serializable {
    private String id;
    private Integer version;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public SegmentReference withId(String str) {
        this.id = str;
        return this;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer num) {
        this.version = num;
    }

    public SegmentReference withVersion(Integer num) {
        this.version = num;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getId() != null) {
            sb.append("Id: " + getId() + ",");
        }
        if (getVersion() != null) {
            sb.append("Version: " + getVersion());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getId() == null ? 0 : getId().hashCode()) + 31) * 31) + (getVersion() != null ? getVersion().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SegmentReference)) {
            return false;
        }
        SegmentReference segmentReference = (SegmentReference) obj;
        if ((segmentReference.getId() == null) ^ (getId() == null)) {
            return false;
        }
        if (segmentReference.getId() != null && !segmentReference.getId().equals(getId())) {
            return false;
        }
        if ((segmentReference.getVersion() == null) ^ (getVersion() == null)) {
            return false;
        }
        return segmentReference.getVersion() == null || segmentReference.getVersion().equals(getVersion());
    }
}
