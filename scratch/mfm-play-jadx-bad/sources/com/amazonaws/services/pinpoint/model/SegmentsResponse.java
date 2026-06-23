package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SegmentsResponse implements Serializable {
    private List<SegmentResponse> item;
    private String nextToken;

    public List<SegmentResponse> getItem() {
        return this.item;
    }

    public void setItem(Collection<SegmentResponse> collection) {
        if (collection == null) {
            this.item = null;
        } else {
            this.item = new ArrayList(collection);
        }
    }

    public SegmentsResponse withItem(SegmentResponse... segmentResponseArr) {
        if (getItem() == null) {
            this.item = new ArrayList(segmentResponseArr.length);
        }
        for (SegmentResponse segmentResponse : segmentResponseArr) {
            this.item.add(segmentResponse);
        }
        return this;
    }

    public SegmentsResponse withItem(Collection<SegmentResponse> collection) {
        setItem(collection);
        return this;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String str) {
        this.nextToken = str;
    }

    public SegmentsResponse withNextToken(String str) {
        this.nextToken = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getItem() != null) {
            sb.append("Item: " + getItem() + ",");
        }
        if (getNextToken() != null) {
            sb.append("NextToken: " + getNextToken());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getItem() == null ? 0 : getItem().hashCode()) + 31) * 31) + (getNextToken() != null ? getNextToken().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SegmentsResponse)) {
            return false;
        }
        SegmentsResponse segmentsResponse = (SegmentsResponse) obj;
        if ((segmentsResponse.getItem() == null) ^ (getItem() == null)) {
            return false;
        }
        if (segmentsResponse.getItem() != null && !segmentsResponse.getItem().equals(getItem())) {
            return false;
        }
        if ((segmentsResponse.getNextToken() == null) ^ (getNextToken() == null)) {
            return false;
        }
        return segmentsResponse.getNextToken() == null || segmentsResponse.getNextToken().equals(getNextToken());
    }
}
