package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SegmentGroup implements Serializable {
    private List<SegmentDimensions> dimensions;
    private List<SegmentReference> sourceSegments;
    private String sourceType;
    private String type;

    public List<SegmentDimensions> getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(Collection<SegmentDimensions> collection) {
        if (collection == null) {
            this.dimensions = null;
        } else {
            this.dimensions = new ArrayList(collection);
        }
    }

    public SegmentGroup withDimensions(SegmentDimensions... segmentDimensionsArr) {
        if (getDimensions() == null) {
            this.dimensions = new ArrayList(segmentDimensionsArr.length);
        }
        for (SegmentDimensions segmentDimensions : segmentDimensionsArr) {
            this.dimensions.add(segmentDimensions);
        }
        return this;
    }

    public SegmentGroup withDimensions(Collection<SegmentDimensions> collection) {
        setDimensions(collection);
        return this;
    }

    public List<SegmentReference> getSourceSegments() {
        return this.sourceSegments;
    }

    public void setSourceSegments(Collection<SegmentReference> collection) {
        if (collection == null) {
            this.sourceSegments = null;
        } else {
            this.sourceSegments = new ArrayList(collection);
        }
    }

    public SegmentGroup withSourceSegments(SegmentReference... segmentReferenceArr) {
        if (getSourceSegments() == null) {
            this.sourceSegments = new ArrayList(segmentReferenceArr.length);
        }
        for (SegmentReference segmentReference : segmentReferenceArr) {
            this.sourceSegments.add(segmentReference);
        }
        return this;
    }

    public SegmentGroup withSourceSegments(Collection<SegmentReference> collection) {
        setSourceSegments(collection);
        return this;
    }

    public String getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(String str) {
        this.sourceType = str;
    }

    public SegmentGroup withSourceType(String str) {
        this.sourceType = str;
        return this;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType.toString();
    }

    public SegmentGroup withSourceType(SourceType sourceType) {
        this.sourceType = sourceType.toString();
        return this;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public SegmentGroup withType(String str) {
        this.type = str;
        return this;
    }

    public void setType(Type type) {
        this.type = type.toString();
    }

    public SegmentGroup withType(Type type) {
        this.type = type.toString();
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getDimensions() != null) {
            sb.append("Dimensions: " + getDimensions() + ",");
        }
        if (getSourceSegments() != null) {
            sb.append("SourceSegments: " + getSourceSegments() + ",");
        }
        if (getSourceType() != null) {
            sb.append("SourceType: " + getSourceType() + ",");
        }
        if (getType() != null) {
            sb.append("Type: " + getType());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((getDimensions() == null ? 0 : getDimensions().hashCode()) + 31) * 31) + (getSourceSegments() == null ? 0 : getSourceSegments().hashCode())) * 31) + (getSourceType() == null ? 0 : getSourceType().hashCode())) * 31) + (getType() != null ? getType().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SegmentGroup)) {
            return false;
        }
        SegmentGroup segmentGroup = (SegmentGroup) obj;
        if ((segmentGroup.getDimensions() == null) ^ (getDimensions() == null)) {
            return false;
        }
        if (segmentGroup.getDimensions() != null && !segmentGroup.getDimensions().equals(getDimensions())) {
            return false;
        }
        if ((segmentGroup.getSourceSegments() == null) ^ (getSourceSegments() == null)) {
            return false;
        }
        if (segmentGroup.getSourceSegments() != null && !segmentGroup.getSourceSegments().equals(getSourceSegments())) {
            return false;
        }
        if ((segmentGroup.getSourceType() == null) ^ (getSourceType() == null)) {
            return false;
        }
        if (segmentGroup.getSourceType() != null && !segmentGroup.getSourceType().equals(getSourceType())) {
            return false;
        }
        if ((segmentGroup.getType() == null) ^ (getType() == null)) {
            return false;
        }
        return segmentGroup.getType() == null || segmentGroup.getType().equals(getType());
    }
}
