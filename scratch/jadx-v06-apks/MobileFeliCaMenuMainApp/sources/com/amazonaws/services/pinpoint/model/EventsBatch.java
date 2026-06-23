package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class EventsBatch implements Serializable {
    private PublicEndpoint endpoint;
    private Map<String, Event> events;

    public PublicEndpoint getEndpoint() {
        return this.endpoint;
    }

    public void setEndpoint(PublicEndpoint publicEndpoint) {
        this.endpoint = publicEndpoint;
    }

    public EventsBatch withEndpoint(PublicEndpoint publicEndpoint) {
        this.endpoint = publicEndpoint;
        return this;
    }

    public Map<String, Event> getEvents() {
        return this.events;
    }

    public void setEvents(Map<String, Event> map) {
        this.events = map;
    }

    public EventsBatch withEvents(Map<String, Event> map) {
        this.events = map;
        return this;
    }

    public EventsBatch addEventsEntry(String str, Event event) {
        if (this.events == null) {
            this.events = new HashMap();
        }
        if (this.events.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.events.put(str, event);
        return this;
    }

    public EventsBatch clearEventsEntries() {
        this.events = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getEndpoint() != null) {
            sb.append("Endpoint: " + getEndpoint() + ",");
        }
        if (getEvents() != null) {
            sb.append("Events: " + getEvents());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getEndpoint() == null ? 0 : getEndpoint().hashCode()) + 31) * 31) + (getEvents() != null ? getEvents().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EventsBatch)) {
            return false;
        }
        EventsBatch eventsBatch = (EventsBatch) obj;
        if ((eventsBatch.getEndpoint() == null) ^ (getEndpoint() == null)) {
            return false;
        }
        if (eventsBatch.getEndpoint() != null && !eventsBatch.getEndpoint().equals(getEndpoint())) {
            return false;
        }
        if ((eventsBatch.getEvents() == null) ^ (getEvents() == null)) {
            return false;
        }
        return eventsBatch.getEvents() == null || eventsBatch.getEvents().equals(getEvents());
    }
}
