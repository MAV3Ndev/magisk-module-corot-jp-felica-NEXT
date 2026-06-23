package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class QuietTime implements Serializable {
    private String end;
    private String start;

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String str) {
        this.end = str;
    }

    public QuietTime withEnd(String str) {
        this.end = str;
        return this;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String str) {
        this.start = str;
    }

    public QuietTime withStart(String str) {
        this.start = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getEnd() != null) {
            sb.append("End: " + getEnd() + ",");
        }
        if (getStart() != null) {
            sb.append("Start: " + getStart());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getEnd() == null ? 0 : getEnd().hashCode()) + 31) * 31) + (getStart() != null ? getStart().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof QuietTime)) {
            return false;
        }
        QuietTime quietTime = (QuietTime) obj;
        if ((quietTime.getEnd() == null) ^ (getEnd() == null)) {
            return false;
        }
        if (quietTime.getEnd() != null && !quietTime.getEnd().equals(getEnd())) {
            return false;
        }
        if ((quietTime.getStart() == null) ^ (getStart() == null)) {
            return false;
        }
        return quietTime.getStart() == null || quietTime.getStart().equals(getStart());
    }
}
