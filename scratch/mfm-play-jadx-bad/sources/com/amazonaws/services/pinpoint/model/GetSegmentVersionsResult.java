package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentVersionsResult implements Serializable {
    private SegmentsResponse segmentsResponse;

    public SegmentsResponse getSegmentsResponse() {
        return this.segmentsResponse;
    }

    public void setSegmentsResponse(SegmentsResponse segmentsResponse) {
        this.segmentsResponse = segmentsResponse;
    }

    public GetSegmentVersionsResult withSegmentsResponse(SegmentsResponse segmentsResponse) {
        this.segmentsResponse = segmentsResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getSegmentsResponse() != null) {
            sb.append("SegmentsResponse: " + getSegmentsResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getSegmentsResponse() == null ? 0 : getSegmentsResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetSegmentVersionsResult)) {
            return false;
        }
        GetSegmentVersionsResult getSegmentVersionsResult = (GetSegmentVersionsResult) obj;
        if ((getSegmentVersionsResult.getSegmentsResponse() == null) ^ (getSegmentsResponse() == null)) {
            return false;
        }
        return getSegmentVersionsResult.getSegmentsResponse() == null || getSegmentVersionsResult.getSegmentsResponse().equals(getSegmentsResponse());
    }
}
