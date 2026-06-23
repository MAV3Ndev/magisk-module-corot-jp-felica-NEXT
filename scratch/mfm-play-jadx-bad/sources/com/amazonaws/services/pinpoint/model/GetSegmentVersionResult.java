package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentVersionResult implements Serializable {
    private SegmentResponse segmentResponse;

    public SegmentResponse getSegmentResponse() {
        return this.segmentResponse;
    }

    public void setSegmentResponse(SegmentResponse segmentResponse) {
        this.segmentResponse = segmentResponse;
    }

    public GetSegmentVersionResult withSegmentResponse(SegmentResponse segmentResponse) {
        this.segmentResponse = segmentResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getSegmentResponse() != null) {
            sb.append("SegmentResponse: " + getSegmentResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getSegmentResponse() == null ? 0 : getSegmentResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetSegmentVersionResult)) {
            return false;
        }
        GetSegmentVersionResult getSegmentVersionResult = (GetSegmentVersionResult) obj;
        if ((getSegmentVersionResult.getSegmentResponse() == null) ^ (getSegmentResponse() == null)) {
            return false;
        }
        return getSegmentVersionResult.getSegmentResponse() == null || getSegmentVersionResult.getSegmentResponse().equals(getSegmentResponse());
    }
}
