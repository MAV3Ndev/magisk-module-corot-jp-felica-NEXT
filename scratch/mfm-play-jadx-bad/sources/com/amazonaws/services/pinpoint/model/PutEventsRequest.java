package com.amazonaws.services.pinpoint.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class PutEventsRequest extends AmazonWebServiceRequest implements Serializable {
    private String applicationId;
    private EventsRequest eventsRequest;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public PutEventsRequest withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public EventsRequest getEventsRequest() {
        return this.eventsRequest;
    }

    public void setEventsRequest(EventsRequest eventsRequest) {
        this.eventsRequest = eventsRequest;
    }

    public PutEventsRequest withEventsRequest(EventsRequest eventsRequest) {
        this.eventsRequest = eventsRequest;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getEventsRequest() != null) {
            sb.append("EventsRequest: " + getEventsRequest());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getEventsRequest() != null ? getEventsRequest().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof PutEventsRequest)) {
            return false;
        }
        PutEventsRequest putEventsRequest = (PutEventsRequest) obj;
        if ((putEventsRequest.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (putEventsRequest.getApplicationId() != null && !putEventsRequest.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((putEventsRequest.getEventsRequest() == null) ^ (getEventsRequest() == null)) {
            return false;
        }
        return putEventsRequest.getEventsRequest() == null || putEventsRequest.getEventsRequest().equals(getEventsRequest());
    }
}
