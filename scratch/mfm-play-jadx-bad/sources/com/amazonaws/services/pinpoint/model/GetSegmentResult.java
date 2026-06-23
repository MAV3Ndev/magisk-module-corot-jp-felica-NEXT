package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentResult implements Serializable {
    private SegmentResponse segmentResponse;

    public SegmentResponse getSegmentResponse() {
        return this.segmentResponse;
    }

    public void setSegmentResponse(SegmentResponse segmentResponse) {
        this.segmentResponse = segmentResponse;
    }

    public GetSegmentResult withSegmentResponse(SegmentResponse segmentResponse) {
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
        if (obj == null || !(obj instanceof GetSegmentResult)) {
            return false;
        }
        GetSegmentResult getSegmentResult = (GetSegmentResult) obj;
        if ((getSegmentResult.getSegmentResponse() == null) ^ (getSegmentResponse() == null)) {
            return false;
        }
        return getSegmentResult.getSegmentResponse() == null || getSegmentResult.getSegmentResponse().equals(getSegmentResponse());
    }
}
