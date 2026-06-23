package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class Session implements Serializable {
    private Integer duration;
    private String id;
    private String startTimestamp;
    private String stopTimestamp;

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer num) {
        this.duration = num;
    }

    public Session withDuration(Integer num) {
        this.duration = num;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public Session withId(String str) {
        this.id = str;
        return this;
    }

    public String getStartTimestamp() {
        return this.startTimestamp;
    }

    public void setStartTimestamp(String str) {
        this.startTimestamp = str;
    }

    public Session withStartTimestamp(String str) {
        this.startTimestamp = str;
        return this;
    }

    public String getStopTimestamp() {
        return this.stopTimestamp;
    }

    public void setStopTimestamp(String str) {
        this.stopTimestamp = str;
    }

    public Session withStopTimestamp(String str) {
        this.stopTimestamp = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getDuration() != null) {
            sb.append("Duration: " + getDuration() + ",");
        }
        if (getId() != null) {
            sb.append("Id: " + getId() + ",");
        }
        if (getStartTimestamp() != null) {
            sb.append("StartTimestamp: " + getStartTimestamp() + ",");
        }
        if (getStopTimestamp() != null) {
            sb.append("StopTimestamp: " + getStopTimestamp());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((getDuration() == null ? 0 : getDuration().hashCode()) + 31) * 31) + (getId() == null ? 0 : getId().hashCode())) * 31) + (getStartTimestamp() == null ? 0 : getStartTimestamp().hashCode())) * 31) + (getStopTimestamp() != null ? getStopTimestamp().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Session)) {
            return false;
        }
        Session session = (Session) obj;
        if ((session.getDuration() == null) ^ (getDuration() == null)) {
            return false;
        }
        if (session.getDuration() != null && !session.getDuration().equals(getDuration())) {
            return false;
        }
        if ((session.getId() == null) ^ (getId() == null)) {
            return false;
        }
        if (session.getId() != null && !session.getId().equals(getId())) {
            return false;
        }
        if ((session.getStartTimestamp() == null) ^ (getStartTimestamp() == null)) {
            return false;
        }
        if (session.getStartTimestamp() != null && !session.getStartTimestamp().equals(getStartTimestamp())) {
            return false;
        }
        if ((session.getStopTimestamp() == null) ^ (getStopTimestamp() == null)) {
            return false;
        }
        return session.getStopTimestamp() == null || session.getStopTimestamp().equals(getStopTimestamp());
    }
}
