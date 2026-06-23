package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SegmentGroupList implements Serializable {
    private List<SegmentGroup> groups;
    private String include;

    public List<SegmentGroup> getGroups() {
        return this.groups;
    }

    public void setGroups(Collection<SegmentGroup> collection) {
        if (collection == null) {
            this.groups = null;
        } else {
            this.groups = new ArrayList(collection);
        }
    }

    public SegmentGroupList withGroups(SegmentGroup... segmentGroupArr) {
        if (getGroups() == null) {
            this.groups = new ArrayList(segmentGroupArr.length);
        }
        for (SegmentGroup segmentGroup : segmentGroupArr) {
            this.groups.add(segmentGroup);
        }
        return this;
    }

    public SegmentGroupList withGroups(Collection<SegmentGroup> collection) {
        setGroups(collection);
        return this;
    }

    public String getInclude() {
        return this.include;
    }

    public void setInclude(String str) {
        this.include = str;
    }

    public SegmentGroupList withInclude(String str) {
        this.include = str;
        return this;
    }

    public void setInclude(Include include) {
        this.include = include.toString();
    }

    public SegmentGroupList withInclude(Include include) {
        this.include = include.toString();
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getGroups() != null) {
            sb.append("Groups: " + getGroups() + ",");
        }
        if (getInclude() != null) {
            sb.append("Include: " + getInclude());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getGroups() == null ? 0 : getGroups().hashCode()) + 31) * 31) + (getInclude() != null ? getInclude().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SegmentGroupList)) {
            return false;
        }
        SegmentGroupList segmentGroupList = (SegmentGroupList) obj;
        if ((segmentGroupList.getGroups() == null) ^ (getGroups() == null)) {
            return false;
        }
        if (segmentGroupList.getGroups() != null && !segmentGroupList.getGroups().equals(getGroups())) {
            return false;
        }
        if ((segmentGroupList.getInclude() == null) ^ (getInclude() == null)) {
            return false;
        }
        return segmentGroupList.getInclude() == null || segmentGroupList.getInclude().equals(getInclude());
    }
}
