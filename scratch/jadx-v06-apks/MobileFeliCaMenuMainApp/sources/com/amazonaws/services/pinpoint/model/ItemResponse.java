package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ItemResponse implements Serializable {
    private EndpointItemResponse endpointItemResponse;
    private Map<String, EventItemResponse> eventsItemResponse;

    public EndpointItemResponse getEndpointItemResponse() {
        return this.endpointItemResponse;
    }

    public void setEndpointItemResponse(EndpointItemResponse endpointItemResponse) {
        this.endpointItemResponse = endpointItemResponse;
    }

    public ItemResponse withEndpointItemResponse(EndpointItemResponse endpointItemResponse) {
        this.endpointItemResponse = endpointItemResponse;
        return this;
    }

    public Map<String, EventItemResponse> getEventsItemResponse() {
        return this.eventsItemResponse;
    }

    public void setEventsItemResponse(Map<String, EventItemResponse> map) {
        this.eventsItemResponse = map;
    }

    public ItemResponse withEventsItemResponse(Map<String, EventItemResponse> map) {
        this.eventsItemResponse = map;
        return this;
    }

    public ItemResponse addEventsItemResponseEntry(String str, EventItemResponse eventItemResponse) {
        if (this.eventsItemResponse == null) {
            this.eventsItemResponse = new HashMap();
        }
        if (this.eventsItemResponse.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.eventsItemResponse.put(str, eventItemResponse);
        return this;
    }

    public ItemResponse clearEventsItemResponseEntries() {
        this.eventsItemResponse = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getEndpointItemResponse() != null) {
            sb.append("EndpointItemResponse: " + getEndpointItemResponse() + ",");
        }
        if (getEventsItemResponse() != null) {
            sb.append("EventsItemResponse: " + getEventsItemResponse());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getEndpointItemResponse() == null ? 0 : getEndpointItemResponse().hashCode()) + 31) * 31) + (getEventsItemResponse() != null ? getEventsItemResponse().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ItemResponse)) {
            return false;
        }
        ItemResponse itemResponse = (ItemResponse) obj;
        if ((itemResponse.getEndpointItemResponse() == null) ^ (getEndpointItemResponse() == null)) {
            return false;
        }
        if (itemResponse.getEndpointItemResponse() != null && !itemResponse.getEndpointItemResponse().equals(getEndpointItemResponse())) {
            return false;
        }
        if ((itemResponse.getEventsItemResponse() == null) ^ (getEventsItemResponse() == null)) {
            return false;
        }
        return itemResponse.getEventsItemResponse() == null || itemResponse.getEventsItemResponse().equals(getEventsItemResponse());
    }
}
