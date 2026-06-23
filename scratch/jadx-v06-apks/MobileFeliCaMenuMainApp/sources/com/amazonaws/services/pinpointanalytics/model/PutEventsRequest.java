package com.amazonaws.services.pinpointanalytics.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PutEventsRequest extends AmazonWebServiceRequest implements Serializable {
    private String clientContext;
    private String clientContextEncoding;
    private List<Event> events;

    public List<Event> getEvents() {
        return this.events;
    }

    public void setEvents(Collection<Event> collection) {
        if (collection == null) {
            this.events = null;
        } else {
            this.events = new ArrayList(collection);
        }
    }

    public PutEventsRequest withEvents(Event... eventArr) {
        if (getEvents() == null) {
            this.events = new ArrayList(eventArr.length);
        }
        for (Event event : eventArr) {
            this.events.add(event);
        }
        return this;
    }

    public PutEventsRequest withEvents(Collection<Event> collection) {
        setEvents(collection);
        return this;
    }

    public String getClientContext() {
        return this.clientContext;
    }

    public void setClientContext(String str) {
        this.clientContext = str;
    }

    public PutEventsRequest withClientContext(String str) {
        this.clientContext = str;
        return this;
    }

    public String getClientContextEncoding() {
        return this.clientContextEncoding;
    }

    public void setClientContextEncoding(String str) {
        this.clientContextEncoding = str;
    }

    public PutEventsRequest withClientContextEncoding(String str) {
        this.clientContextEncoding = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getEvents() != null) {
            sb.append("events: " + getEvents() + ",");
        }
        if (getClientContext() != null) {
            sb.append("clientContext: " + getClientContext() + ",");
        }
        if (getClientContextEncoding() != null) {
            sb.append("clientContextEncoding: " + getClientContextEncoding());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((getEvents() == null ? 0 : getEvents().hashCode()) + 31) * 31) + (getClientContext() == null ? 0 : getClientContext().hashCode())) * 31) + (getClientContextEncoding() != null ? getClientContextEncoding().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof PutEventsRequest)) {
            return false;
        }
        PutEventsRequest putEventsRequest = (PutEventsRequest) obj;
        if ((putEventsRequest.getEvents() == null) ^ (getEvents() == null)) {
            return false;
        }
        if (putEventsRequest.getEvents() != null && !putEventsRequest.getEvents().equals(getEvents())) {
            return false;
        }
        if ((putEventsRequest.getClientContext() == null) ^ (getClientContext() == null)) {
            return false;
        }
        if (putEventsRequest.getClientContext() != null && !putEventsRequest.getClientContext().equals(getClientContext())) {
            return false;
        }
        if ((putEventsRequest.getClientContextEncoding() == null) ^ (getClientContextEncoding() == null)) {
            return false;
        }
        return putEventsRequest.getClientContextEncoding() == null || putEventsRequest.getClientContextEncoding().equals(getClientContextEncoding());
    }
}
