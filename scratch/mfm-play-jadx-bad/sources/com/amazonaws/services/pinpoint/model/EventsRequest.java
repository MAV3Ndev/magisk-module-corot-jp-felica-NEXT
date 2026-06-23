package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class EventsRequest implements Serializable {
    private Map<String, EventsBatch> batchItem;

    public Map<String, EventsBatch> getBatchItem() {
        return this.batchItem;
    }

    public void setBatchItem(Map<String, EventsBatch> map) {
        this.batchItem = map;
    }

    public EventsRequest withBatchItem(Map<String, EventsBatch> map) {
        this.batchItem = map;
        return this;
    }

    public EventsRequest addBatchItemEntry(String str, EventsBatch eventsBatch) {
        if (this.batchItem == null) {
            this.batchItem = new HashMap();
        }
        if (this.batchItem.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.batchItem.put(str, eventsBatch);
        return this;
    }

    public EventsRequest clearBatchItemEntries() {
        this.batchItem = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getBatchItem() != null) {
            sb.append("BatchItem: " + getBatchItem());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getBatchItem() == null ? 0 : getBatchItem().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EventsRequest)) {
            return false;
        }
        EventsRequest eventsRequest = (EventsRequest) obj;
        if ((eventsRequest.getBatchItem() == null) ^ (getBatchItem() == null)) {
            return false;
        }
        return eventsRequest.getBatchItem() == null || eventsRequest.getBatchItem().equals(getBatchItem());
    }
}
