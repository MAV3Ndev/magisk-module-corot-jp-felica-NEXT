package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class EventsResponse implements Serializable {
    private Map<String, ItemResponse> results;

    public Map<String, ItemResponse> getResults() {
        return this.results;
    }

    public void setResults(Map<String, ItemResponse> map) {
        this.results = map;
    }

    public EventsResponse withResults(Map<String, ItemResponse> map) {
        this.results = map;
        return this;
    }

    public EventsResponse addResultsEntry(String str, ItemResponse itemResponse) {
        if (this.results == null) {
            this.results = new HashMap();
        }
        if (this.results.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.results.put(str, itemResponse);
        return this;
    }

    public EventsResponse clearResultsEntries() {
        this.results = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getResults() != null) {
            sb.append("Results: " + getResults());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getResults() == null ? 0 : getResults().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EventsResponse)) {
            return false;
        }
        EventsResponse eventsResponse = (EventsResponse) obj;
        if ((eventsResponse.getResults() == null) ^ (getResults() == null)) {
            return false;
        }
        return eventsResponse.getResults() == null || eventsResponse.getResults().equals(getResults());
    }
}
