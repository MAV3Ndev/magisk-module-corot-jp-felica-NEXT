package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetEventStreamResult implements Serializable {
    private EventStream eventStream;

    public EventStream getEventStream() {
        return this.eventStream;
    }

    public void setEventStream(EventStream eventStream) {
        this.eventStream = eventStream;
    }

    public GetEventStreamResult withEventStream(EventStream eventStream) {
        this.eventStream = eventStream;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getEventStream() != null) {
            sb.append("EventStream: " + getEventStream());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getEventStream() == null ? 0 : getEventStream().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetEventStreamResult)) {
            return false;
        }
        GetEventStreamResult getEventStreamResult = (GetEventStreamResult) obj;
        if ((getEventStreamResult.getEventStream() == null) ^ (getEventStream() == null)) {
            return false;
        }
        return getEventStreamResult.getEventStream() == null || getEventStreamResult.getEventStream().equals(getEventStream());
    }
}
