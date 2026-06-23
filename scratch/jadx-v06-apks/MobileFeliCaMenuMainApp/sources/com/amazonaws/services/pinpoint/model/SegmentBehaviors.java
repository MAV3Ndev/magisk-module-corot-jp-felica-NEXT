package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SegmentBehaviors implements Serializable {
    private RecencyDimension recency;

    public RecencyDimension getRecency() {
        return this.recency;
    }

    public void setRecency(RecencyDimension recencyDimension) {
        this.recency = recencyDimension;
    }

    public SegmentBehaviors withRecency(RecencyDimension recencyDimension) {
        this.recency = recencyDimension;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getRecency() != null) {
            sb.append("Recency: " + getRecency());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getRecency() == null ? 0 : getRecency().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SegmentBehaviors)) {
            return false;
        }
        SegmentBehaviors segmentBehaviors = (SegmentBehaviors) obj;
        if ((segmentBehaviors.getRecency() == null) ^ (getRecency() == null)) {
            return false;
        }
        return segmentBehaviors.getRecency() == null || segmentBehaviors.getRecency().equals(getRecency());
    }
}
