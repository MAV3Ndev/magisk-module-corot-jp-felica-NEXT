package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class DeleteEventStreamResult implements Serializable {
    private EventStream eventStream;

    public EventStream getEventStream() {
        return this.eventStream;
    }

    public void setEventStream(EventStream eventStream) {
        this.eventStream = eventStream;
    }

    public DeleteEventStreamResult withEventStream(EventStream eventStream) {
        this.eventStream = eventStream;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
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
        if (obj == null || !(obj instanceof DeleteEventStreamResult)) {
            return false;
        }
        DeleteEventStreamResult deleteEventStreamResult = (DeleteEventStreamResult) obj;
        if ((deleteEventStreamResult.getEventStream() == null) ^ (getEventStream() == null)) {
            return false;
        }
        return deleteEventStreamResult.getEventStream() == null || deleteEventStreamResult.getEventStream().equals(getEventStream());
    }
}
