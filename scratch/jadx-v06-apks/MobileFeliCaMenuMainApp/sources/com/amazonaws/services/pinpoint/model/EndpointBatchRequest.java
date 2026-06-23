package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class EndpointBatchRequest implements Serializable {
    private List<EndpointBatchItem> item;

    public List<EndpointBatchItem> getItem() {
        return this.item;
    }

    public void setItem(Collection<EndpointBatchItem> collection) {
        if (collection == null) {
            this.item = null;
        } else {
            this.item = new ArrayList(collection);
        }
    }

    public EndpointBatchRequest withItem(EndpointBatchItem... endpointBatchItemArr) {
        if (getItem() == null) {
            this.item = new ArrayList(endpointBatchItemArr.length);
        }
        for (EndpointBatchItem endpointBatchItem : endpointBatchItemArr) {
            this.item.add(endpointBatchItem);
        }
        return this;
    }

    public EndpointBatchRequest withItem(Collection<EndpointBatchItem> collection) {
        setItem(collection);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getItem() != null) {
            sb.append("Item: " + getItem());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getItem() == null ? 0 : getItem().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EndpointBatchRequest)) {
            return false;
        }
        EndpointBatchRequest endpointBatchRequest = (EndpointBatchRequest) obj;
        if ((endpointBatchRequest.getItem() == null) ^ (getItem() == null)) {
            return false;
        }
        return endpointBatchRequest.getItem() == null || endpointBatchRequest.getItem().equals(getItem());
    }
}
