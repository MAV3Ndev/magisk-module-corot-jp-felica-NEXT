package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class PutEventsResult implements Serializable {
    private EventsResponse eventsResponse;

    public EventsResponse getEventsResponse() {
        return this.eventsResponse;
    }

    public void setEventsResponse(EventsResponse eventsResponse) {
        this.eventsResponse = eventsResponse;
    }

    public PutEventsResult withEventsResponse(EventsResponse eventsResponse) {
        this.eventsResponse = eventsResponse;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getEventsResponse() != null) {
            sb.append("EventsResponse: " + getEventsResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getEventsResponse() == null ? 0 : getEventsResponse().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof PutEventsResult)) {
            return false;
        }
        PutEventsResult putEventsResult = (PutEventsResult) obj;
        if ((putEventsResult.getEventsResponse() == null) ^ (getEventsResponse() == null)) {
            return false;
        }
        return putEventsResult.getEventsResponse() == null || putEventsResult.getEventsResponse().equals(getEventsResponse());
    }
}
